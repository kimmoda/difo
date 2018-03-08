(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['feedcard'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.isAddButton : depth0),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.program(4, data, 0),"data":data})) != null ? stack1 : "");
},"2":function(container,depth0,helpers,partials,data) {
    return "        <div class=\"stamp add-post-button\">\n            <div class=\"zion-feed-container\">\n                <div class=\"zion-feed-content dash\">\n                    <div>\n                        <img src=\"https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/icon/addpost.png\"\n                             alt=\"Add Post\">\n                    </div>\n                </div>\n                <div class=\"zion-feed-footer\">\n                    <div class=\"zion-feed-author-info\">\n                        <div class=\"no-padding interact\">\n                            <div class=\"zion-feed-like-plugin-container\"></div>\n                        </div>\n                    </div>\n                    <div class=\"action-container\">\n                        <div class=\"tag\">\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n";
},"4":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression, alias5=container.lambda;

  return "        <div class=\"grid-item\" id=\"feed-"
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" data-feed-id=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\">\n            <div class=\"zion-feed-container\">\n                <div class=\"zion-feed-content\">\n                    <div class=\"feed-image\" id=\""
    + alias4(alias5((depth0 != null ? depth0.id : depth0), depth0))
    + "\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1),{"name":"if","hash":{},"fn":container.program(5, data, 0),"inverse":container.program(7, data, 0),"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.isShowEditBtn : depth0),{"name":"if","hash":{},"fn":container.program(9, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                    </div>\n                </div>\n                <div class=\"zion-feed-footer\">\n                    <div class=\"zion-feed-author-info\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.isShowAuthor : depth0),{"name":"if","hash":{},"fn":container.program(11, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                        <div class=\""
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.isShowAuthor : depth0),{"name":"if","hash":{},"fn":container.program(13, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + " no-padding interact\" id=\""
    + alias4(alias5((depth0 != null ? depth0.id : depth0), depth0))
    + "\"\n                             data-feedId=\""
    + alias4(alias5((depth0 != null ? depth0.id : depth0), depth0))
    + "\">\n                            <div class=\"zion-feed-like-plugin-container\" data-feed-like-id=\""
    + alias4(alias5((depth0 != null ? depth0.id : depth0), depth0))
    + "\"\n                                 data-feed-like=\""
    + alias4(alias5((depth0 != null ? depth0.like : depth0), depth0))
    + "\" data-feed-like-count=\""
    + alias4(((helper = (helper = helpers.likeCount || (depth0 != null ? depth0.likeCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"likeCount","hash":{},"data":data}) : helper)))
    + "\"></div>\n                        </div>\n                    </div>\n                    <div class=\"action-container\">\n                        <div class=\"tag\">\n"
    + ((stack1 = helpers.each.call(alias1,(depth0 != null ? depth0.feedTags : depth0),{"name":"each","hash":{},"fn":container.program(15, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n";
},"5":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "                            <img class=\"main-image\" src=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.coverImage : depth0)) != null ? stack1.url : stack1), depth0))
    + "\" alt=\""
    + alias2(alias1((depth0 != null ? depth0.title : depth0), depth0))
    + "\">\n";
},"7":function(container,depth0,helpers,partials,data) {
    return "                            <img class=\"main-image\"\n                                 src=\"https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/icon/defaultImage.png\"\n                                 alt=\"Draft\">\n";
},"9":function(container,depth0,helpers,partials,data) {
    return "                            <div class=\"edit-button-container\" data-feedId=\""
    + container.escapeExpression(container.lambda((depth0 != null ? depth0.id : depth0), depth0))
    + "\">\n                                <i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i>\n                            </div>\n";
},"11":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "                            <div class=\"col-xs-9 no-padding author-info-container\"\n                                 data-user-id=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.userId : stack1), depth0))
    + "\">\n                                <img class=\"img-circle image-avatar img-responsive\" src=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\"\n                                     alt=\"avatar\"/>\n                                <div class=\"author-name\" title=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">\n                                    "
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.author : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\n                                </div>\n                            </div>\n";
},"13":function(container,depth0,helpers,partials,data) {
    return "col-xs-3";
},"15":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                                <span class=\"zion-feed-tag\" data-tag-name=\""
    + alias4(((helper = (helper = helpers.name || (depth0 != null ? depth0.name : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"name","hash":{},"data":data}) : helper)))
    + "\"\n                                      data-tag-code=\""
    + alias4(((helper = (helper = helpers.code || (depth0 != null ? depth0.code : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"code","hash":{},"data":data}) : helper)))
    + "\">#"
    + alias4(((helper = (helper = helpers.name || (depth0 != null ? depth0.name : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"name","hash":{},"data":data}) : helper)))
    + "</span>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.data : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();