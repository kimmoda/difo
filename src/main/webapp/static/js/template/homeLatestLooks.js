(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['homeLatestLooks'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=container.lambda, alias2=container.escapeExpression;

  return "    <section class=\"item col-lg-3 col-md-4 col-sm-6\">\n        <div class=\"look-image\" style=\"background-image: url("
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1), depth0))
    + ")\" data-id=\""
    + alias2(alias1((depth0 != null ? depth0.id : depth0), depth0))
    + "\"></div>\n        <div class=\"author-container row\">\n            <div class=\"col-xs-9 author-info-container\">\n                <img class=\"img-circle image-avatar img-responsive author-link\" data-user-id=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.userId : stack1), depth0))
    + "\" src=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\" alt=\"avatar\"/>\n                <div class=\"author-name author-link\" data-user-id=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.userId : stack1), depth0))
    + "\" title=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">\n                    "
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\n                </div>\n            </div>\n            <div class=\"col-xs-3 interact\">\n                <div class=\"interact-container\">\n                    <div class=\"zion-feed-like-plugin-container\" data-feed-like-id=\""
    + alias2(alias1((depth0 != null ? depth0.id : depth0), depth0))
    + "\" data-feed-like=\""
    + alias2(alias1((depth0 != null ? depth0.like : depth0), depth0))
    + "\" data-feed-like-count=\""
    + alias2(((helper = (helper = helpers.likeCount || (depth0 != null ? depth0.likeCount : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"likeCount","hash":{},"data":data}) : helper)))
    + "\"></div>\n                </div>\n            </div>\n        </div>\n    </section>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.looks : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "<section class=\"item col-lg-3 col-md-4 col-sm-6\"></section>";
},"useData":true});
})();