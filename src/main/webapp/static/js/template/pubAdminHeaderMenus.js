(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['pubAdminHeaderMenus'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "    <a class=\"dash-border-button btn "
    + ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.active : depth0),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\" href=\""
    + alias2(alias1((depth0 != null ? depth0.url : depth0), depth0))
    + "\">\n    "
    + alias2(alias1((depth0 != null ? depth0.label : depth0), depth0))
    + "\n    </a>\n";
},"2":function(container,depth0,helpers,partials,data) {
    return "zion-admin-header-active";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.pubAuthHeaderMenus : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "<i class=\"dashboard-show-more-button fa fa-ellipsis-v zion-mobile-visible\"\n   id=\"dashboard-show-more-button\" aria-hidden=\"true\"></i>";
},"useData":true});
})();