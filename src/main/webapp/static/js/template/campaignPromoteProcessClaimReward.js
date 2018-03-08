(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['campaignPromoteProcessClaimReward'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"promote-campaign-processing-container promote-campaign-clam-reward-container\">\n    <div class=\"processing-icon\">\n        <i class=\"fa fa-4x fa-check-circle-o\" aria-hidden=\"true\"></i>\n    </div>\n    <p class=\"processing-title\">\n        Well done!\n    </p>\n    <p class=\"processing-message-1\">\n        Thank you for participating in this campaign.\n        <br>\n        This could be your lucky day, give your luck a try and you could win up to "
    + alias4(((helper = (helper = helpers.maxCoinEarn || (depth0 != null ? depth0.maxCoinEarn : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"maxCoinEarn","hash":{},"data":data}) : helper)))
    + "&nbsp;"
    + alias4(((helper = (helper = helpers.coinName || (depth0 != null ? depth0.coinName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"coinName","hash":{},"data":data}) : helper)))
    + " coins.\n    </p>\n    <button class=\"promote-campaign-claim-btn btn zion-btn-black\" id=\"promote-campaign-claim-btn\">\n        I'm Feeling Lucky!\n    </button>\n</div>\n\n";
},"useData":true});
})();