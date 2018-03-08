(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['pubHeaderMenus'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "                    <li class=\""
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.active : depth0),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + " "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.isMobile : depth0),{"name":"if","hash":{},"fn":container.program(4, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + " "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.isDesktop : depth0),{"name":"if","hash":{},"fn":container.program(6, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\" id=\""
    + container.escapeExpression(container.lambda((depth0 != null ? depth0.id : depth0), depth0))
    + "\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disableHyperlink : depth0),{"name":"if","hash":{},"fn":container.program(8, data, 0),"inverse":container.program(10, data, 0),"data":data})) != null ? stack1 : "")
    + "                    </li>\n";
},"2":function(container,depth0,helpers,partials,data) {
    return "active";
},"4":function(container,depth0,helpers,partials,data) {
    return "zion-mobile-visible";
},"6":function(container,depth0,helpers,partials,data) {
    return "zion-desktop-visible";
},"8":function(container,depth0,helpers,partials,data) {
    var alias1=container.lambda, alias2=container.escapeExpression;

  return "                            <a href=\"javascript:void(0);\" title=\"menu-"
    + alias2(alias1((depth0 != null ? depth0.label : depth0), depth0))
    + "\">"
    + alias2(alias1((depth0 != null ? depth0.label : depth0), depth0))
    + "</a>\n";
},"10":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {});

  return "                            <a href=\""
    + alias2(alias1((depth0 != null ? depth0.url : depth0), depth0))
    + "\"\n                               "
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.externalLink : depth0),{"name":"if","hash":{},"fn":container.program(11, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + " title=\"menu-"
    + alias2(alias1((depth0 != null ? depth0.label : depth0), depth0))
    + "\">\n                                <span class=\""
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.highlight : depth0),{"name":"if","hash":{},"fn":container.program(13, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\">"
    + alias2(alias1((depth0 != null ? depth0.label : depth0), depth0))
    + "</span>\n                            </a>\n";
},"11":function(container,depth0,helpers,partials,data) {
    return "target=\"_blank\"";
},"13":function(container,depth0,helpers,partials,data) {
    return "highlight";
},"15":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                            <img src=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.user : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\" class=\"img-circle\" alt=\"avatar\"/>\n";
},"17":function(container,depth0,helpers,partials,data) {
    return "                            <img src=\"https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/avatar.png\"\n                                 class=\"img-circle\" alt=\"avatar\"/>\n                            <span>ME</span>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "<div class=\"main_menu\">\n    <div class=\"navbar text-right\">\n        <div class=\"navbar-header\">\n            <div>\n                <button id=\"header-menu\" type=\"button\" class=\"navbar-toggle collapse-header-menu\" data-toggle=\"collapse\"\n                        data-target=\".menu-collapse\">\n                    <span class=\"sr-only\">Toggle navigation</span>\n                    <span class=\"icon-bar\"></span>\n                    <span class=\"icon-bar\"></span>\n                    <span class=\"icon-bar\"></span>\n                </button>\n            </div>\n        </div>\n        <div class=\"navbar-collapse collapse menu-collapse\">\n            <ul class=\"nav navbar-nav navbar-right\">\n"
    + ((stack1 = helpers.each.call(alias1,(depth0 != null ? depth0.pubHeaderMenus : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <li class=\"zion-desktop-visible\" role=\"presentation\">\n                    <a id=\"avatar\" class=\"avatar\" data-toggle=\"dropdown\" href=\"#\" role=\"button\"\n                       aria-haspopup=\"true\" aria-expanded=\"false\"\n                       title=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.user : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.user : depth0)) != null ? stack1.avatar : stack1),{"name":"if","hash":{},"fn":container.program(15, data, 0),"inverse":container.program(17, data, 0),"data":data})) != null ? stack1 : "")
    + "                    </a>\n                </li>\n            </ul>\n        </div>\n    </div>\n</div>\n    ";
},"useData":true});
})();