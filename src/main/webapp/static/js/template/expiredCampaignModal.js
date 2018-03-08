(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['expiredCampaignModal'] = template({"1":function(container,depth0,helpers,partials,data) {
    return "zion-hide";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "<div class=\"modal-dialog campaign-modal-container\" role=\"document\">\n    <div class=\"modal-content\">\n        <div class=\"modal-body\">\n            <div class=\"top-bar clearfix\">\n                <div class=\"modal-close\">\n                    <i class=\"fa fa-lg fa-chevron-left\" aria-hidden=\"true\" data-dismiss=\"modal\" aria-label=\"Close\"></i>\n                </div>\n                <div class=\"mobile-top-bar-title\">\n                    <h4 class=\"mobile-top-bar-title-content\">Expired Campaigns</h4>\n                </div>\n            </div>\n            <button type=\"button\" class=\"modal-close-btn close\" data-dismiss=\"modal\" aria-label=\"Close\">\n                <span class=\"cancel-text\" aria-hidden=\"true\">&times;</span>\n            </button>\n            <div class=\"zion-expired-campaigns-container\">\n                \n            </div>\n            <div class=\"zion-show-more-button-container\">\n                <button class=\"btn btn-primary show-more-expired-campaign-bt "
    + ((stack1 = helpers.unless.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.nextPageToken : depth0),{"name":"unless","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\">Show More</button>\n            </div>\n         </div>\n    </div>\n</div>";
},"useData":true});
})();