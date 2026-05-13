package com.campaign.manager.exception;

import lombok.Getter;

@Getter
public class ResourceInUseException extends RuntimeException {
    private final String resourceType;
    private final Long resourceId;
    private final int usageCount;

    public ResourceInUseException(String resourceType, Long resourceId, int usageCount) {
        super(String.format("Cannot delete %s with id %d because it is used by %d campaign(s)",
                resourceType, resourceId, usageCount));
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.usageCount = usageCount;
    }
}
