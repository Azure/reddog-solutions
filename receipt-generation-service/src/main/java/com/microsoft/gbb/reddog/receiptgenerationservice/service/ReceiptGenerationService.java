package com.microsoft.gbb.reddog.receiptgenerationservice.service;

import com.microsoft.gbb.reddog.receiptgenerationservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.receiptgenerationservice.exception.ReceiptSaveException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Receipt generation service
 */
@Slf4j
@Component
public class ReceiptGenerationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptGenerationService.class);

    @Qualifier("azureStorageBlobProtocolResolver")
    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${data.BLOB.CONTAINER}")
    private String receiptsStorageBlobContainer;

    @Value("${spring.cloud.azure.storage.blob.endpoint}")
    private String azureStorageEndpoint;

    public ReceiptGenerationService() {
    }


    public String generateReceipt(OrderSummaryDto orderSummary) {
        LOGGER.info("Generating receipt");
        try {
            return writeBlobFile(orderSummary, orderSummary.getOrderId().toString()+".json");
        } catch (IOException e) {
            throw new ReceiptSaveException("Error saving receipt");
        }
    }

    public String writeBlobFile(OrderSummaryDto orderSummary, String filename) throws IOException {
        String link = String.format("azure-blob://%s/receipts/%s", receiptsStorageBlobContainer, filename);
        Resource blobFile = resourceLoader.getResource(link);
        try (OutputStream os = ((WritableResource) blobFile).getOutputStream()) {
            os.write(orderSummary.toString().getBytes());
        }
        return String.format("%s/%s/%s", azureStorageEndpoint, receiptsStorageBlobContainer, filename);
    }

}
