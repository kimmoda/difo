<html>
<head>
    <title>${data.emailTitle}</title>
    <style>
        a {
            text-decoration: none
        }
    </style>

    <link rel='stylesheet'
          href='https://fonts.googleapis.com/css?family=Lato%3A300%7CMontserrat%3A400%2C700%2C300%7CPlayfair+Display%3A400italic%7CPermanent+Marker%3A400&#038;subset=latin&#038;ver=4.5.9'
          type='text/css' media='all'/>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="100%"
       style="background-color:#F8F8F8; font-family: montserrat,serif" height="100%">
    <tbody>
    <tr>
        <td valign="top" style="padding-top:30px; padding-bottom: 30px;" align="center">
            <table border="0" cellpadding="0" cellspacing="0" width="80%" bgcolor="#FFFFFF"
                   style="border:1px solid #CFCFCF; max-width: 800px">
                <th height="80" bgcolor="black" style="color: white; text-align: left" colspan="2">
                    <img src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/logo_white.png" alt="Logo"
                         style="height: 60px">
                </th>
                <tbody>
                <tr>
                    <td height="40" style="font-size:8px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="border-collapse:collapse;padding-left:40px;padding-right:40px;" colspan="2">
                        <div style="font-size:18px;color:#222222;font-family:Arial, Helvetica, sans-serif;">
                            Hello ${data.receiverName},
                        </div>
                    </td>
                </tr>
                <tr>
                    <td height="20" style="font-size:8px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="border-collapse:collapse;padding-left:40px;padding-right:40px;" colspan="2">
                        <div style="font-size:14px;line-height:20px;color:#484848;font-family:Arial, Helvetica, sans-serif;">
                            <p>
                                Thank you again for your application to become a verified influencer.
                            </p>
                            <p>
                                We have received your application, but it looks like we still need some additional information.
                            </p>
                            <p>
                                ${data.requestedInfo}
                            </p>
                            
                            <p>
                                <b>What do you need to do?</b>
                            </p>
                            
                            <p>
                                Simply reply to this email. Once we receive this information, we will finish processing your Influencer application, and provide you with a decision ASAP.
                            </p>
                            <p>
                                Looking forward to hearing from you soon,
                            </p>
                            <p>
                                The ${data.brandName} Team
                            </p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td height="40" style="font-size:8px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="text-align: center;" colspan="2">
                        <a href="${data.homeUrl}/campaign">
                            <div style="background-color: black; color: white; display: inline-block; padding: 10px">
                                PARTICIPATE IN CAMPAIGNS
                            </div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td height="40" style="font-size:8px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="text-align: center;" colspan="2">
                        <h4>
                            ABOUT ${data.brandName}
                        </h4>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center; padding-left: 40px; padding-right: 40px; font-weight: 100; font-size: 12px"
                        colspan="2">
                        <div style="background-color: #f0f0f0; padding:10px">
                            <p>
                                ${data.brandName} is for fashion, beauty and home decor influencers wanting to increase visibility online.
                            </p>
                            <ol>
                                <li>Get involved. Help a community of talented influencers to grow their online engagement.</li>
                                <li>Get rewarded. Participate in other influencer's campaigns to earn WHB coins and rewards from ${data.brandName}.</li>
                                <li>Promote your content. Create promotional campaigns and invite fellow influencers to engage with your content while inspiring fans to join and support you as well.</li>
                            </ol>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td height="40" style="font-size:8px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="text-align: center;" colspan="2">
                        <h4>
                            CONNECT WITH US
                        </h4>
                    </td>
                </tr>
                <tr>
                    <td height="40" style="text-align: center" colspan="2">
                        <div style="display: inline-block; margin-right: 20px">
                            <a href="https://www.instagram.com/stylehubapp/?hl=en" target="_blank"
                               title="instagram">
                                <img src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/socialicon/instagram_circle.png"
                                     alt="Instagram" style="height: 40px; width: 40px">
                            </a>
                        </div>
                        <div style="display: inline-block; margin-right: 20px">
                            <a href="https://www.facebook.com/stylehubapp/" target="_blank" title="facebook">
                                <img src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/socialicon/facebook_circle.png"
                                     alt="Facebook" style="height: 40px; width: 40px">
                            </a>
                        </div>
                        <div style="display: inline-block;">
                            <a href="https://www.linkedin.com/company/25040505/" target="_blank"
                               title="linkedin">
                                <img src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/socialicon/linkedin_circle.png"
                                     alt="Linkedin" style="height: 40px; width: 40px">
                            </a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td height="40" style="font-size:8px;" colspan="2">&nbsp;</td>
                </tr>
                <tr style="border-top: 2px solid #7d7d7d">
                    <td height="80">
                        <img src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/stylehub_logo.png" alt="LOGO"
                             style="height: 60px">
                    </td>
                    <td style="text-align: right; padding-right: 40px; font-size: 12px">
                        <div>
                            <#--STYL 659 need update support email to dynamic data-->
                            <a href="${data.supportEmail}" style="color: #7d7d7d">${data.supportEmail}</a>
                        </div>
                        <div>
                            <a href="${data.homeUrl}" style="color: #7d7d7d">${data.homeUrl}</a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td height="40"
                        style="background-color: black; text-align: center; color: white; font-size: 12px; font-weight: 100"
                        colspan="2">
                        Copyright &copy; ${.now?string('yyyy')} ${data.brandName}. All rights reserved.
                    </td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>