/*******************************************************************************
 *  Forward Thinking CONFIDENTIAL
 *   __________________
 *
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains
 *  the property of Forward Thinking Ltd and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to Forward Thinking Ltd
 *  and its suppliers and may be covered by New Zealand and Foreign Patents,
 *  patents in process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Forward Thinking Ltd.
 */

package com.zion.converter;

import com.zion.feed.ShopTag;
import com.zion.morphia.entity.embed.ShopTagEntity;

import java.util.ArrayList;
import java.util.List;

public class ShopTagConverter {
    public List<ShopTag> convertToModel(List<ShopTagEntity> shopTagEntities) {
        if (shopTagEntities == null) {
            return new ArrayList<>();
        }

        List<ShopTag> shopTags = new ArrayList<>();
        for (ShopTagEntity entity : shopTagEntities) {
            ShopTag shopTag = new ShopTag();
            shopTag.setTagID(entity.getTagID());
            shopTag.setTitle(entity.getTitle());
            shopTag.setUrl(entity.getUrl());
            shopTags.add(shopTag);
        }

        return shopTags;
    }

    public List<ShopTagEntity> convertToEntity(List<ShopTag> shopTags) {

        if (shopTags != null) {
            List<ShopTagEntity> shopTagEntities = new ArrayList<>();
            for (ShopTag item : shopTags) {
                ShopTagEntity shopTagEntity = new ShopTagEntity();
                shopTagEntity.setTagID(item.getTagID());
                shopTagEntity.setTitle(item.getTitle());
                shopTagEntity.setUrl(item.getUrl());
                shopTagEntities.add(shopTagEntity);
            }
            return shopTagEntities;
        }
        return new ArrayList<>();
    }
}
