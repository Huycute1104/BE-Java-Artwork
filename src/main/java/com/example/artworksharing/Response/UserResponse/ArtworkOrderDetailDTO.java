package com.example.artworksharing.Response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkOrderDetailDTO {
    private int artworkId;
    private String artworkName;
    private String artworkUrl;
    private double price;
    private int likeCount;
    private int commentCount;
    private int buyCount;
    private int userId;
    private int orderDetailId;
}
