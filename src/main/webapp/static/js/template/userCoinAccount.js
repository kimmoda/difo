(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['userCoinAccount'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"account-campaign panel panel-default\">\n    <div class=\"account-info-container\">\n        <div class=\"title\">Account</div>\n        <div class=\"account\">\n            <div class=\"account-row-container income\">\n                <div class=\"account-title\">\n                    Income\n                </div>\n                <div class=\"amount income-amount\">"
    + alias4(((helper = (helper = helpers.income || (depth0 != null ? depth0.income : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"income","hash":{},"data":data}) : helper)))
    + "&nbsp;"
    + alias4(((helper = (helper = helpers.currencyName || (depth0 != null ? depth0.currencyName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"currencyName","hash":{},"data":data}) : helper)))
    + "</div>\n            </div>\n            <div class=\"account-row-container expense\">\n                <div class=\"account-title\">\n                    Expense\n                </div>\n                <div class=\"amount expense-amount\">-&nbsp;"
    + alias4(((helper = (helper = helpers.expense || (depth0 != null ? depth0.expense : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"expense","hash":{},"data":data}) : helper)))
    + "&nbsp;"
    + alias4(((helper = (helper = helpers.currencyName || (depth0 != null ? depth0.currencyName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"currencyName","hash":{},"data":data}) : helper)))
    + "</div>\n            </div>\n            <hr class=\"line\">\n            <div class=\"account-row-container balance\">\n                <div class=\"account-title\">\n                    Balance\n                </div>\n                <div class=\"amount balance-amount\">"
    + alias4(((helper = (helper = helpers.balance || (depth0 != null ? depth0.balance : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"balance","hash":{},"data":data}) : helper)))
    + "&nbsp;"
    + alias4(((helper = (helper = helpers.currencyName || (depth0 != null ? depth0.currencyName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"currencyName","hash":{},"data":data}) : helper)))
    + "</div>\n            </div>\n        </div>\n    </div>\n</div>";
},"useData":true});
})();