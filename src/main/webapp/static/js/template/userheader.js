(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['userheader'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                    <button id=\"follow-user-button\" class=\"follow-btn btn btn-default "
    + alias4(((helper = (helper = helpers.following || (depth0 != null ? depth0.following : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"following","hash":{},"data":data}) : helper)))
    + "\">"
    + alias4(((helper = (helper = helpers.followText || (depth0 != null ? depth0.followText : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"followText","hash":{},"data":data}) : helper)))
    + "</button>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"zion-user-header container\">\r\n    <div class=\"avatar col-sm-6\">\r\n        <img class=\"zion-circle-avatar-lg\" src=\""
    + alias4(((helper = (helper = helpers.avatar || (depth0 != null ? depth0.avatar : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"avatar","hash":{},"data":data}) : helper)))
    + "\" alt=\"avatar\" />\r\n    </div>\r\n    <div class=\"advice-btn col-sm-6\">\r\n            <div class=\"user-react-button-group\">\r\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.notMine : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <button id=\"share-user-button\" class=\"share-user-btn btn btn-default\"><i class=\"fa fa-share-alt\" aria-hidden=\"true\"></i></button>\r\n            </div>\r\n            <div class=\"user-share-container\">\r\n                <div id=\"zion-user-social-share-container\" class=\"zion-social-share-plugin-container\" data-share-url=\""
    + alias4(((helper = (helper = helpers.shortUrl || (depth0 != null ? depth0.shortUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"shortUrl","hash":{},"data":data}) : helper)))
    + "\" data-share-title=\""
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\" data-share-description=\""
    + alias4(((helper = (helper = helpers.content || (depth0 != null ? depth0.content : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"content","hash":{},"data":data}) : helper)))
    + "\" data-share-image=\""
    + alias4(container.lambda(((stack1 = (depth0 != null ? depth0.photo : depth0)) != null ? stack1.url : stack1), depth0))
    + "\"></div>\r\n            </div>\r\n    </div>\r\n</div>";
},"useData":true});
})();