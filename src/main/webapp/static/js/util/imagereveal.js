/**
 * config can be overriden depends on how do you want to write give your css name in your image grid layout.
 * 
 * For example:
 * <div id="feed-list" class="grid">
 *  <div class="grid-col-sizer"></div>
 *  <div class="grid-gutter-sizer"></div>
 *  <div class="grid-item">
        <div class="feed-image" id="{{this.id}}">
            <img class="img-rounded zion-main-image" src="{{this.photo.url}}" alt="{{this.title}}">
        </div>
    </div>
    <div class="grid-item">
        <div class="feed-image" id="{{this.id}}">
            <img class="img-rounded zion-main-image" src="{{this.photo.url}}" alt="{{this.title}}">
        </div>
    </div>
 *</div>
 */
var zionImageReveal = {
	init: function(config) {
		zionImageReveal.config = {
			mainImageCssName: 'main-image', //We need to add this css class in our main image grid, Otherwise, cannot be loaded.
			gridSelector: '.grid',
			itemSelector: '.grid-item',
			columnWidth: '.grid-col-sizer',
			gutter: '.grid-gutter-sizer',
            stamp: '.stamp'
		}
		if(config) {
			jQuery.extend(zionImageReveal.config, config);
		}
		return zionImageReveal.createImageGrid(zionImageReveal.config);
	},
	
	createImageGrid: function(config) {
		var grid = jQuery(config.gridSelector).masonry({
			itemSelector: config.itemSelector, // select none at first
			columnWidth: config.columnWidth,
			gutter: config.gutter,
			percentPosition: true,
			stagger: 30,
			visibleStyle: {transform: 'translateY(0)', opacity: 1},
			hiddenStyle: {transform: 'translateY(100px)', opacity: 0},
			stamp: config.stamp
		}),
		msnry = grid.data('masonry');

		jQuery.fn.masonryImagesReveal = function ($items) {
			var itemSelector = msnry.options.itemSelector;
			jQuery.each($items, function (index, $item) {
               if (jQuery($item).hasClass('stamp')){
                   msnry.appended($item);
                   grid.masonry('stamp', $item);
               }else {
                   jQuery($item).hide();
               }
            });
			this.append($items);
			$items.imagesLoaded().progress(function (imgLoad, image) {
				//Do not show the whole grid when image is not main image eg: avatar image. We show the image grid until it is a main image photo.
				if (jQuery(image.img).hasClass(zionImageReveal.config.mainImageCssName)) {
					var $item = jQuery(image.img).parents(itemSelector);
					$item.show();
					msnry.appended($item);
                    grid.masonry('layout');
				}
			});
			return this;
		};
		return grid;
	}
}