var zionUploadPhotoWidget = function () {
    var show = function (callback) {
            cloudinary.openUploadWidget(options, function (error, result) {
                if (result) {
                    // TODO  Google Tracking.
                    callback(result);
                }
            })
        },
        getCloudinarySignatures = function (callback, object) {
            zionWebService.post(zionUrls.rest_cloudinary_signature, object, function (resp) {
                if (resp.data) {
                    callback(resp.data);
                }
            });
        },
        options = {
            cloud_name: webConfig.cloudinaryHostName,
            api_key: webConfig.cloudinaryApiKey,
            cropping: 'server',
            upload_preset: webConfig.cloudinaryPresetId,
            multiple: false,
            resource_type: "image",
            client_allowed_formats: ["png", "gif", "jpeg", "jpg", "jpe", "bmp"],
            max_file_size: 10000000, //10MB
            max_image_width: 1500,
            max_image_height: 1500,
            cropping_validate_dimensions: true,
            secure: true, // Instagram loading error without this option
            show_powered_by: false,
            upload_signature: getCloudinarySignatures,
            sources: ['local', 'instagram', 'facebook'],
            stylesheet: "#cloudinary-overlay.with_theme {\n" +
            "  display: block;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-overlay {\n" +
            "  background-color: rgba(0, 0, 0, 0.7);\n" +
            "}\n" +
            "\n" +
            "#cloudinary-overlay.modal {\n" +
            "  background-color: rgba(0,0,0, 0.5);\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget {\n" +
            "  background: #ffffff;\n" +
            "  -moz-border-radius: 3px;\n" +
            "  -webkit-border-radius: 3px;\n" +
            "  border-radius: 3px;\n" +
            "  border: 1px solid rgb(153, 153, 153);\n" +
            "}\n" +
            "\n" +
            "#cloudinary-navbar {\n" +
            "  background: #f5f5f5;\n" +
            "  border: 1px solid rgba(0, 0, 0, 0.05);\n" +
            "\n" +
            "  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);\n" +
            "  -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);\n" +
            "  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);\n" +
            "\n" +
            "  -webkit-border-radius: 4px;\n" +
            "  -moz-border-radius: 4px;\n" +
            "  border-radius: 4px;\n" +
            "  margin: 20px 20px 10px 20px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-navbar .source {\n" +
            "  border-right: 0px;\n" +
            "  border-bottom: 6px solid;\n" +
            "  border-color: transparent;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-navbar .source.active {\n" +
            "  background: none;\n" +
            "  border-bottom: 6px solid black;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-navbar .source.active .label {\n" +
            "  color: #000;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .drag_area {\n" +
            "  background: #f5f5f5;\n" +
            "  border: 2px dashed rgba(0, 0, 0, 0.1);\n" +
            "  -webkit-border-radius: 4px;\n" +
            "  -moz-border-radius: 4px;\n" +
            "  border-radius: 4px;\n" +
            "  margin: 30px 20px 0px 20px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .drag_area.in {\n" +
            "  border-color: #01BB16\n" +
            "}\n" +
            "\n" +
            "#cloudinary-navbar .sources .source.local .icon,\n" +
            "#cloudinary-navbar .sources .source.url .icon,\n" +
            "#cloudinary-navbar .sources .source.camera .icon,\n" +
            "#cloudinary-navbar .sources .source.image_search .icon,\n" +
            "#cloudinary-navbar .sources .source.dropbox .icon,\n" +
            "#cloudinary-navbar .sources .source.facebook .icon,\n" +
            "#cloudinary-navbar .sources .source.google_photos .icon,\n" +
            "#cloudinary-navbar .sources .source.instagram .icon {\n" +
            "  background-position-x: 0;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-navbar .close {\n" +
            "  color: rgb(85, 85, 85);\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .button, #cloudinary-widget .button.small_button {\n" +
            "  background: black;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .button:hover, #cloudinary-widget .button.small_button:hover, #cloudinary-widget .upload_button_holder:hover .button {\n" +
            "  background: black;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel {\n" +
            "  height: 437px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel.progress .thumbnails .thumbnail .error {\n" +
            "  color: white;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel.progress .thumbnails {\n" +
            "  margin-top: 4px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel.camera .form .button_holder {\n" +
            "  margin-top: 10px;\n" +
            "  margin-bottom: 10px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel.camera .note {\n" +
            "  font-weight: normal;\n" +
            "  font-size: 13px;\n" +
            "  padding: 4px 20px 4px 20px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel.camera .video_holder {\n" +
            "  height: 288px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel.camera video {\n" +
            "  zoom: 0.6;\n" +
            "  -moz-transform: scale(0.6)\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .panel.camera video {\n" +
            "  border-width: 0px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-widget .camera .form {\n" +
            "  background: #f5f5f5;\n" +
            "  border: 1px solid rgba(0, 0, 0, 0.05);\n" +
            "  -webkit-border-radius: 4px;\n" +
            "  -moz-border-radius: 4px;\n" +
            "  border-radius: 4px;\n" +
            "  margin: 0px 20px 0px 20px;\n" +
            "  padding-top: 10px;\n" +
            "}\n" +
            "\n" +
            "#cloudinary-overlay.inline .widget {\n" +
            "  border: 1px solid #ddd;\n" +
            "}\n" +
            "\n" +
            ".widget .panel.local .drag_area .drag_content .label {\n" +
            "  display: block;\n" +
            "  color: #656364;\n" +
            "  font-size: 28px;\n" +
            "  line-height: 35px;\n" +
            "  text-align: center;\n" +
            "  margin-bottom: 20px;\n" +
            "}\n" +
            "\n" +
            ".widget .panel.local .drag_area .drag_content .or {\n" +
            "  display: block;\n" +
            "  text-align: center;\n" +
            "  font-size: 18px;\n" +
            "  line-height: 23px;\n" +
            "  color: black;\n" +
            "  margin-bottom: 20px;\n" +
            "}\n" +
            "\n" +
            ".button {\n" +
            "  color: #fefeff;\n" +
            "  font-size: 18px;\n" +
            "  border-radius: 6px;\n" +
            "  font-weight: 400;\n" +
            "  text-decoration: none;\n" +
            "  display: inline-block;\n" +
            "  padding: 10px 24px;\n" +
            "  background: black;\n" +
            "}\n" +
            "\n" +
            ".button:hover {\n" +
            "  background: black;\n" +
            "}\n" +
            "\n" +
            "#instagram .anon h2 {\n" +
            "  text-align: center;\n" +
            "}\n" +
            "\n" +
            "@media screen and (max-width: 767px) {\n" +
            "  #cloudinary-widget .drag_area {\n" +
            "    border: none;\n" +
            "    background: none;\n" +
            "  }\n" +
            "}\n"
        },
        uploadFeed = function (max_files, callback) {
            // options.cropping = null;
            // options.cropping_validate_dimensions = null;
            // delete options["cropping"];
            // delete options["cropping_validate_dimensions"];
            // if (max_files && max_files > 1) {
                // options.multiple = true;
                // options.max_files = max_files;
                // options = jQuery.extend({}, options, {
                //     multiple: true,
                //     max_files: max_files
                // max_image_width: 2000
                // });
            // }

            show(callback);
        },
        uploadAvatar = function (callback) {
            options = jQuery.extend({}, options, {
                cropping_aspect_ratio: 1
            });
            show(callback);
        };

    return {
        uploadFeedWithCallback: uploadFeed,
        uploadAvatarWithCallback: uploadAvatar,
    }

};