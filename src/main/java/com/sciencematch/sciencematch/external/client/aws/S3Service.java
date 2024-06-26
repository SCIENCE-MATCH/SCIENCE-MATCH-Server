package com.sciencematch.sciencematch.external.client.aws;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }

    public String uploadFile(MultipartFile multipartFile, String folder) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        //inputStream 통해 Byte로 파일이 전달되기 때문에 파일에 대한 정보가 없어서 objectMetadata 같이 넘겨야 함
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize()); //TODO: 파일 크기 제한
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) { //Statement Class의 인스턴스나 Stream 타입의 클래스들이 동작 후 필요로하는 close() 메소드를 자동실행해주는 공간
            amazonS3.putObject(
                new PutObjectRequest(bucket + "/" + folder + "/image", fileName, inputStream,
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket + "/" + folder + "/image", fileName).toString();
        } catch (IOException e) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_SAVE_IMAGE_EXCEPTION,
                ErrorStatus.NOT_FOUND_SAVE_IMAGE_EXCEPTION.getMessage());
        }
    }

    // 파일명 (중복 방지)
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        if (fileName.isEmpty()) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_IMAGE_EXCEPTION,
                ErrorStatus.NOT_FOUND_IMAGE_EXCEPTION.getMessage());
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        fileValidate.add(".pdf");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new CustomException(ErrorStatus.INVALID_MULTIPART_EXTENSION_EXCEPTION,
                ErrorStatus.INVALID_MULTIPART_EXTENSION_EXCEPTION.getMessage());
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void deleteFile(String url) throws IOException {
        try {
            amazonS3Client().deleteObject(bucket, url.substring(56));
        } catch (SdkClientException e) {
            throw new IOException("S3 파일 삭제 오류", e);
        }
    }
}
