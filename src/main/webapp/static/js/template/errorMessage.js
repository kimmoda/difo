(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['errorMessage'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper;

  return "            "
    + container.escapeExpression(((helper = (helper = helpers.message || (depth0 != null ? depth0.message : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"message","hash":{},"data":data}) : helper)))
    + "\n";
},"3":function(container,depth0,helpers,partials,data) {
    return "        An error occurred. We are experiencing a busy network. Please try refreshing the page to solve this issue. \n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"zion-error-message-modal-container\">\n    <p class=\"error-title\">\n        Sorry!\n    </p>\n    <p class=\"error-message\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.message : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.program(3, data, 0),"data":data})) != null ? stack1 : "")
    + "        If you are still having problems, contact us at <a href=\"mailto:"
    + alias4(((helper = (helper = helpers.emailHelper || (depth0 != null ? depth0.emailHelper : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"emailHelper","hash":{},"data":data}) : helper)))
    + "\"><b>"
    + alias4(((helper = (helper = helpers.emailHelper || (depth0 != null ? depth0.emailHelper : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"emailHelper","hash":{},"data":data}) : helper)))
    + "</b></a> and our friendly support team will get\n        back to you shortly.\n    </p>\n</div>";
},"useData":true});
})();