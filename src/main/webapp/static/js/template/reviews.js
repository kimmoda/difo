(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['reviews'] = template({"1":function(container,depth0,helpers,partials,data) {
    return "hide";
},"3":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {}), alias4=helpers.helperMissing;

  return "            <article class=\"review col-sm-12\">\r\n                <div class=\"row\">\r\n                    <div class=\"avatar col-lg-1 col-sm-2 col-xs-12\">\r\n                        <img class=\"zion-circle-avatar\" src=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\">\r\n                    </div>\r\n                    <div class=\"content col-lg-11 col-sm-10 col-xs-12\">\r\n                        <h4 class=\"title\">"
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "</h4>\r\n                        <div class=\"rate\">\r\n                            "
    + ((stack1 = (helpers.listRateStar || (depth0 && depth0.listRateStar) || alias4).call(alias3,(depth0 != null ? depth0.currentRate : depth0),{"name":"listRateStar","hash":{},"fn":container.program(4, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\r\n                        </div>\r\n                        <p class=\"text\">\r\n                            "
    + alias2(((helper = (helper = helpers.comment || (depth0 != null ? depth0.comment : depth0)) != null ? helper : alias4),(typeof helper === "function" ? helper.call(alias3,{"name":"comment","hash":{},"data":data}) : helper)))
    + "\r\n                        </p>\r\n                    </div>\r\n                </div>\r\n            </article>\r\n";
},"4":function(container,depth0,helpers,partials,data) {
    return "";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "<section class=\"zion-stylist-review\">\r\n    <div class=\"row\">\r\n        <section class=\"edit-btn\">\r\n            <a href=\"#\">\r\n                <i id=\"zion-review-stylist\" class=\"fa fa-lg fa-pencil pull-right "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.alreadyRated : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\" aria-hidden=\"true\"></i>\r\n            </a>\r\n        </section>\r\n"
    + ((stack1 = helpers.each.call(alias1,(depth0 != null ? depth0.reviews : depth0),{"name":"each","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "        \r\n    </div>\r\n</section>";
},"useData":true});
})();