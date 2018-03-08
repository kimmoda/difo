(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['campaignItem'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {});

  return "    <div class=\"grid-item\" data-campaign-id=\""
    + alias2(alias1((depth0 != null ? depth0.id : depth0), depth0))
    + "\" data-status=\""
    + alias2(alias1((depth0 != null ? depth0.taskStatus : depth0), depth0))
    + "\" data-enabled=\""
    + alias2(alias1((depth0 != null ? depth0.enabled : depth0), depth0))
    + "\">\n        <div class=\"zion-campaign-container\">\n            <div class=\"zion-campaign-content\">\n                <div class=\"campaign-image\" data-id=\""
    + alias2(alias1((depth0 != null ? depth0.id : depth0), depth0))
    + "\">\n                    <img class=\"main-image img-responsive\"\n                         src=\""
    + ((stack1 = helpers["if"].call(alias3,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.program(4, data, 0),"data":data})) != null ? stack1 : "")
    + "\"\n                         alt=\""
    + alias2(alias1((depth0 != null ? depth0.title : depth0), depth0))
    + "\">\n"
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.enabled : depth0),{"name":"if","hash":{},"fn":container.program(6, data, 0),"inverse":container.program(16, data, 0),"data":data})) != null ? stack1 : "")
    + "\n"
    + ((stack1 = helpers["if"].call(alias3,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1),{"name":"if","hash":{},"fn":container.program(18, data, 0),"inverse":container.program(20, data, 0),"data":data})) != null ? stack1 : "")
    + "                </div>\n\n                <div class=\"zion-campaign-footer\">\n                    <div class=\"zion-campaign-title\">\n                        <span>"
    + alias2(alias1((depth0 != null ? depth0.title : depth0), depth0))
    + "</span>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n";
},"2":function(container,depth0,helpers,partials,data) {
    var stack1;

  return container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1), depth0));
},"4":function(container,depth0,helpers,partials,data) {
    return "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/icon/defaultCampaignImage.png";
},"6":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.isMine : depth0),{"name":"if","hash":{},"fn":container.program(7, data, 0),"inverse":container.program(9, data, 0),"data":data})) != null ? stack1 : "");
},"7":function(container,depth0,helpers,partials,data) {
    return "                            <div class=\"zion-campaign-item-status zion-campaign-item-MINE\">\n                            <span>\n                                Mine\n                            </span>\n                            </div>\n";
},"9":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.expired : depth0),{"name":"if","hash":{},"fn":container.program(10, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.completed : depth0),{"name":"if","hash":{},"fn":container.program(12, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers.unless.call(alias1,(depth0 != null ? depth0.active : depth0),{"name":"unless","hash":{},"fn":container.program(14, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"10":function(container,depth0,helpers,partials,data) {
    return "                                <div class=\"zion-campaign-item-cover zion-campaign-item-expired-cover\"></div>\n";
},"12":function(container,depth0,helpers,partials,data) {
    return "                                <div class=\"zion-campaign-item-cover zion-campaign-item-completed-cover\"></div>\n";
},"14":function(container,depth0,helpers,partials,data) {
    var alias1=container.lambda, alias2=container.escapeExpression;

  return "                                <div class=\"zion-campaign-item-status zion-campaign-item-"
    + alias2(alias1((depth0 != null ? depth0.taskStatus : depth0), depth0))
    + "\">\n                            <span>\n                                "
    + alias2(alias1((depth0 != null ? depth0.taskStatus : depth0), depth0))
    + "\n                            </span>\n                                </div>\n";
},"16":function(container,depth0,helpers,partials,data) {
    return "                        <div class=\"zion-campaign-item-status zion-campaign-item-DELETED\">\n                            <span>\n                                Deleted\n                            </span>\n                        </div>\n";
},"18":function(container,depth0,helpers,partials,data) {
    return "";
},"20":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        <div class=\"favicon\">\n                            <img class=\"img-responsive\" src=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.favicon : stack1), depth0))
    + "\" alt=\"\">\n                        </div>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.looks : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();