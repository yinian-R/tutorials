package com.wymm.miniosample;

import io.minio.BucketExistsArgs;
import io.minio.DownloadObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class _1MinioFileUploadTest {
    
    @Test
    void simple() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://172.25.21.117:23000")
                            .credentials("Svb5IkbVkUNW9qO5", "rcTbHals4dDLALyvUaP7i7udCEX4Nanx")
                            .build();
        
            // Make 'asiatrip' bucket if not exist.
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("asiatrip").build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }
        
            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            ObjectWriteResponse asiatrip = minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("asiatrip")
                            .object("node-v18.14.1-x64.msi")
                            .filename("G:\\node-v18.14.1-x64.msi")
                            .build());
            System.out.println("'G:\\node-v18.14.1-x64.msi' is successfully uploaded as "
                            + "object 'node-v18.14.1-x64.msi' to bucket 'asiatrip'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
    
    @Test
    void download() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://172.25.21.117:23000")
                        .credentials("Svb5IkbVkUNW9qO5", "rcTbHals4dDLALyvUaP7i7udCEX4Nanx")
                        .build();
        
        // Download object given the bucket, object name and output file name
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("asiatrip")
                        .object("node-v18.14.1-x64.msi")
                        .filename("D:\\360MoveData\\Users\\76442\\Desktop\\新建文件夹\\node-v18.14.1-x64.msi")
                        .build());
    }
    
    @Test
    void test() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://172.25.22.28:9000")
                        .credentials("minio", "password")
                        .build();
    
        //ObjectWriteResponse asiatrip = minioClient.uploadObject(
        //        UploadObjectArgs.builder()
        //                .bucket("10000")
        //                .object("AAAAAAA.jpg")
        //                .filename("D:\\360MoveData\\Users\\76442\\Desktop\\AAAAAAA.jpg")
        //                .build());
        //System.out.println(asiatrip.toString());
        
        String url =
                minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket("10000")
                                .object("20230516/491270640641380352.png")
                                .expiry(1, TimeUnit.DAYS)
                                .build());
        System.out.println(url);
    }
}
