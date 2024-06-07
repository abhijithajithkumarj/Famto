package com.Famto.Famto.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteProduct {

    private String categoryId;
    private String merchantId;


}
