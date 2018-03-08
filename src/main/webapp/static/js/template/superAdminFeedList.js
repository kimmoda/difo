(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['superAdminFeedList'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<li class=\"list-group-item\">\n    <span>"
    + alias4(((helper = (helper = helpers.creationDate || (depth0 != null ? depth0.creationDate : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"creationDate","hash":{},"data":data}) : helper)))
    + "</span>\n    <select class=\"feed-rank-selection\" data-feedid=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\">\n        <option value=\"top\" data-feedid=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" "
    + ((stack1 = ((helper = (helper = helpers.topFeedChecked || (depth0 != null ? depth0.topFeedChecked : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"topFeedChecked","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + ">TOP</option>\n        <option value=\"good\" data-feedid=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" "
    + ((stack1 = ((helper = (helper = helpers.goodFeedChecked || (depth0 != null ? depth0.goodFeedChecked : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"goodFeedChecked","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + ">GOOD</option>\n        <option value=\"fair\" data-feedid=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" "
    + ((stack1 = ((helper = (helper = helpers.fairFeedChecked || (depth0 != null ? depth0.fairFeedChecked : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"fairFeedChecked","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + ">FAIR</option>\n        <option value=\"poor\" data-feedid=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\" "
    + ((stack1 = ((helper = (helper = helpers.poorFeedChecked || (depth0 != null ? depth0.poorFeedChecked : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"poorFeedChecked","hash":{},"data":data}) : helper))) != null ? stack1 : "")
    + ">POOR</option>\n    </select>\n    \n    <a href=\""
    + alias4(((helper = (helper = helpers.feedUrl || (depth0 != null ? depth0.feedUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"feedUrl","hash":{},"data":data}) : helper)))
    + "\">\n        <img src=\""
    + alias4(((helper = (helper = helpers.coverImageUrl || (depth0 != null ? depth0.coverImageUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"coverImageUrl","hash":{},"data":data}) : helper)))
    + "\" class=\"rounded float-left\"></img>\n    </a>\n    <span>"
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "</span> <span>("
    + alias4(((helper = (helper = helpers.authorName || (depth0 != null ? depth0.authorName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"authorName","hash":{},"data":data}) : helper)))
    + " | "
    + alias4(((helper = (helper = helpers.authorRoles || (depth0 != null ? depth0.authorRoles : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"authorRoles","hash":{},"data":data}) : helper)))
    + ")</span>--<span>"
    + alias4(((helper = (helper = helpers.feedStatus || (depth0 != null ? depth0.feedStatus : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"feedStatus","hash":{},"data":data}) : helper)))
    + "</span>\n    <button type=\"button\" class=\"btn btn-warning zion-delete-feed\" data-feedid=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\">Delete</button>\n</li>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.data : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();