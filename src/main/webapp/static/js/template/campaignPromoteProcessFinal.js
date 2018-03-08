(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['campaignPromoteProcessFinal'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"promote-campaign-processing-container promote-campaign-final-container\">\n    <div class=\"flip-container\">\n        <div class=\"flipper\">\n            <div class=\"front\">\n                <div class=\"front-content\">\n                    Good Luck\n                </div>\n            </div>\n            <div class=\"back\">\n                <div>\n                    <span class=\"coin-num\">"
    + alias4(((helper = (helper = helpers.earnedCoinAmount || (depth0 != null ? depth0.earnedCoinAmount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"earnedCoinAmount","hash":{},"data":data}) : helper)))
    + "</span>\n                </div>\n                <div class=\"coin-currency\">"
    + alias4(((helper = (helper = helpers.coinName || (depth0 != null ? depth0.coinName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"coinName","hash":{},"data":data}) : helper)))
    + "</div>\n            </div>\n        </div>\n    </div>\n    <p class=\"processing-title\">\n        Congratulations!\n    </p>\n    <p class=\"processing-message-1\">\n        To see your total balance go to your wallet in your profile page\n    </p>\n    <p class=\"processing-message-1\">\n        or keep participating by choosing another campaign.\n    </p>\n</div>\n";
},"useData":true});
})();