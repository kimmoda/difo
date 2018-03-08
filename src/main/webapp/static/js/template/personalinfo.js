(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['personalinfo'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper;

  return "            <article class=\"bio col-xs-12\">\r\n                <h3 class=\"title\">Bio</h3>\r\n                <p class=\"content\">\r\n                    "
    + container.escapeExpression(((helper = (helper = helpers.introduction || (depth0 != null ? depth0.introduction : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"introduction","hash":{},"data":data}) : helper)))
    + "\r\n                </p>\r\n            </article>\r\n";
},"3":function(container,depth0,helpers,partials,data) {
    return "            <article class=\"bio col-xs-12\">\r\n                <h3>This fashion trendsetter hasn't added any about info yet.\r\n                    <i class=\"fa fa-frown-o\" aria-hidden=\"true\"></i>\r\n                </h3>\r\n            </article>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "<section class=\"zion-stylist-profile\">\r\n    <div class=\"row\">\r\n"
    + ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.introduction : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.program(3, data, 0),"data":data})) != null ? stack1 : "")
    + "    </div>\r\n</section>";
},"useData":true});
})();