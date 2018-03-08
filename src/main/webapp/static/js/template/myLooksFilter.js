(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['myLooksFilter'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "<div class=\"col-sm-12 right-content\">\r\n    <span class=\"zion-looks-status draft look-draft-link\">Draft("
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.data : depth0)) != null ? stack1.draftCount : stack1), depth0))
    + ")</span>\r\n    <span class=\"zion-looks-status published look-final-link\">Published("
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.data : depth0)) != null ? stack1.finalCount : stack1), depth0))
    + ")</span>\r\n</div>\r\n";
},"useData":true});
})();