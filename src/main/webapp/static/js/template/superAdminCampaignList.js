(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['superAdminCampaignList'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<li class=\"list-group-item\">\n    <a href=\""
    + alias4(((helper = (helper = helpers.updateCampaignUrl || (depth0 != null ? depth0.updateCampaignUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"updateCampaignUrl","hash":{},"data":data}) : helper)))
    + "\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.coverImageUrl : depth0),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.program(4, data, 0),"data":data})) != null ? stack1 : "")
    + "        <span>"
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "</span> <span>("
    + alias4(((helper = (helper = helpers.authorName || (depth0 != null ? depth0.authorName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"authorName","hash":{},"data":data}) : helper)))
    + " | </span>--<span>"
    + alias4(((helper = (helper = helpers.taskStatus || (depth0 != null ? depth0.taskStatus : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"taskStatus","hash":{},"data":data}) : helper)))
    + "</span>\n    </a>\n    <div>\n        <span>Creation Date: "
    + alias4(((helper = (helper = helpers.creationDate || (depth0 != null ? depth0.creationDate : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"creationDate","hash":{},"data":data}) : helper)))
    + "</span>\n        <span> Expired Date"
    + alias4(((helper = (helper = helpers.expiredDate || (depth0 != null ? depth0.expiredDate : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"expiredDate","hash":{},"data":data}) : helper)))
    + "</span>\n        <a href=\""
    + alias4(((helper = (helper = helpers.postUrl || (depth0 != null ? depth0.postUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"postUrl","hash":{},"data":data}) : helper)))
    + "\">Promotion Post Url</a>\n        <button type=\"button\" class=\"btn btn-warning zion-delete-campaign\" data-campaignid=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\">Delete</button>\n    </div>\n</li>\n";
},"2":function(container,depth0,helpers,partials,data) {
    var helper;

  return "            <img src=\""
    + container.escapeExpression(((helper = (helper = helpers.coverImageUrl || (depth0 != null ? depth0.coverImageUrl : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"coverImageUrl","hash":{},"data":data}) : helper)))
    + "\" class=\"rounded float-left zion-thumbnail\"></img>\n";
},"4":function(container,depth0,helpers,partials,data) {
    return "            <span class=\"glyphicon glyphicon-link\"></span>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.data : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();