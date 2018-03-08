/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Forward Thinking Ltd and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Forward Thinking Ltd
 * and its suppliers and may be covered by New Zealand and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Forward Thinking Ltd.
 */
package com.zion.user;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zion.common.CodeSet;

@JsonInclude(Include.NON_NULL)
public class Contact {

    private String email;

    private String address1;

    private String address2;

    private String suburb;

    private String city;

    private CodeSet country;

    private String state;

    private String postcode;

    private String placeId;

    private String phone1;

    private String phone2;

    private String mobile;

    private String website;

    private String facebook;

    private String instagram;

    private String youtube;

    private String blog;

    public Contact() {
        super();
    }

    public Contact(String email, String address1, String address2, String suburb, String city, CodeSet country, String state,
            String postcode, String placeId, String phone1, String phone2, String mobile, String website, String facebook,
            String instagram, String youtube, String blog) {
        this.email = StringUtils.trimToNull(email);
        this.address1 = StringUtils.trimToNull(address1);
        this.address2 = StringUtils.trimToNull(address2);
        this.suburb = StringUtils.trimToNull(suburb);
        this.city = StringUtils.trimToNull(city);
        this.country = country;
        this.state = StringUtils.trimToNull(state);
        this.postcode = StringUtils.trimToNull(postcode);
        this.placeId = StringUtils.trimToNull(placeId);
        this.phone1 = StringUtils.trimToNull(phone1);
        this.phone2 = StringUtils.trimToNull(phone2);
        this.mobile = StringUtils.trimToNull(mobile);
        this.website = StringUtils.trimToNull(website);
        this.facebook = StringUtils.trimToNull(facebook);
        this.instagram = StringUtils.trimToNull(instagram);
        this.youtube = StringUtils.trimToNull(youtube);
        this.blog = StringUtils.trimToNull(blog);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public CodeSet getCountry() {
        return country;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(CodeSet country) {
        this.country = country;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
