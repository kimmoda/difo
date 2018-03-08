(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['pubTabBarMenus'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.icon : depth0),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"2":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=container.lambda, alias3=container.escapeExpression;

  return "        <div class=\"tab-item hidden-md hidden-lg\">\n            <a class=\"tab-item-link "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.active : depth0),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + " "
    + alias3(alias2((depth0 != null ? depth0.id : depth0), depth0))
    + "\"\n               href=\""
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disableHyperlink : depth0),{"name":"if","hash":{},"fn":container.program(5, data, 0),"inverse":container.program(7, data, 0),"data":data})) != null ? stack1 : "")
    + "\"\n               title=\"menu-"
    + alias3(alias2((depth0 != null ? depth0.label : depth0), depth0))
    + "\">\n                <i class=\"icon fa fa-2x "
    + alias3(alias2((depth0 != null ? depth0.icon : depth0), depth0))
    + "\" aria-hidden=\"true\"></i>\n                <span class=\"tabbar-label\">"
    + alias3(alias2((depth0 != null ? depth0.label : depth0), depth0))
    + "</span>\n            </a>\n        </div>\n";
},"3":function(container,depth0,helpers,partials,data) {
    return "active";
},"5":function(container,depth0,helpers,partials,data) {
    return "javascript:void(0);";
},"7":function(container,depth0,helpers,partials,data) {
    return container.escapeExpression(container.lambda((depth0 != null ? depth0.url : depth0), depth0));
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.pubTabBarMenus : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();