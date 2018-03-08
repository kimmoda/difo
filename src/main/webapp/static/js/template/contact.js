(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['contact'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "            <article class=\"contact col-xs-12\">\n                <h3 class=\"title\">Contact</h3>\n                <p class=\"phone\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.phone1 : stack1),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </p>\n                <p class=\"email\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.email : stack1),{"name":"if","hash":{},"fn":container.program(4, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </p>\n                <p class=\"website\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.website : stack1),{"name":"if","hash":{},"fn":container.program(6, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </p>\n                <section class=\"social-link\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.facebook : stack1),{"name":"if","hash":{},"fn":container.program(8, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.youtube : stack1),{"name":"if","hash":{},"fn":container.program(10, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.instagram : stack1),{"name":"if","hash":{},"fn":container.program(12, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </section>\n            </article>\n";
},"2":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        Phone: "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.phone1 : stack1), depth0))
    + "\n";
},"4":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "                        Email: <a href=\"mailto:"
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.email : stack1), depth0))
    + "\">"
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.email : stack1), depth0))
    + "</a>\n";
},"6":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "                        Website: <a href=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.website : stack1), depth0))
    + "\" target=\"_blank\">"
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.website : stack1), depth0))
    + "</a>\n";
},"8":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        <a href=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.facebook : stack1), depth0))
    + "\" target=\"_blank\">\n                            <i class=\"fa fa-2x fa-facebook-square\" aria-hidden=\"true\"></i>\n                        </a>\n";
},"10":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        <a href=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.youtube : stack1), depth0))
    + "\" target=\"_blank\">\n                            <i class=\"fa fa-2x fa-youtube-square\" aria-hidden=\"true\"></i>\n                        </a>\n";
},"12":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        <a href=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.contact : depth0)) != null ? stack1.instagram : stack1), depth0))
    + "\" target=\"_blank\">\n                            <i class=\"fa fa-2x fa-instagram\" aria-hidden=\"true\"></i>\n                        </a>\n";
},"14":function(container,depth0,helpers,partials,data) {
    return "            <article class=\"col-xs-12\">\n                <h3>This fashion trendsetter hasn't added any contact info yet.\n                    <i class=\"fa fa-frown-o\" aria-hidden=\"true\"></i>\n                </h3>\n            </article>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "<section class=\"zion-stylist-contact\">\n    <div class=\"row\">\n"
    + ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.contact : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.program(14, data, 0),"data":data})) != null ? stack1 : "")
    + "    </div>\n</section>";
},"useData":true});
})();