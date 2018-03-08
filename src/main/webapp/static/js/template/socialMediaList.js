(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['socialMediaList'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper;

  return "    <span>\n        <a href=\""
    + container.escapeExpression(((helper = (helper = helpers.facebook || (depth0 != null ? depth0.facebook : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"facebook","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n            <i class=\"fa fa-facebook\" aria-hidden=\"true\"></i>\n        </a>\n    </span>\n";
},"3":function(container,depth0,helpers,partials,data) {
    var helper;

  return "    <span>\n        <a href=\""
    + container.escapeExpression(((helper = (helper = helpers.instagram || (depth0 != null ? depth0.instagram : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"instagram","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n            <i class=\"fa fa-instagram\" aria-hidden=\"true\"></i>\n        </a>\n    </span>\n";
},"5":function(container,depth0,helpers,partials,data) {
    var helper;

  return "    <span>\n        <a href=\""
    + container.escapeExpression(((helper = (helper = helpers.youtube || (depth0 != null ? depth0.youtube : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"youtube","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n            <i class=\"fa fa-youtube-play\" aria-hidden=\"true\"></i>\n        </a>\n    </span>\n";
},"7":function(container,depth0,helpers,partials,data) {
    var helper;

  return "    <span>\n        <a href=\""
    + container.escapeExpression(((helper = (helper = helpers.linkedin || (depth0 != null ? depth0.linkedin : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"linkedin","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n            <i class=\"fa fa-linkedin\" aria-hidden=\"true\"></i>\n        </a>\n    </span>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.facebook : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.instagram : depth0),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.youtube : depth0),{"name":"if","hash":{},"fn":container.program(5, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.linkedin : depth0),{"name":"if","hash":{},"fn":container.program(7, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();