package com.fc.ishop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author florence
 * @date 2023/11/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileOwnerDto {
    // 拥有者id
    private String ownerId;
    // 用户类型
    private String userEnums;
}
