$(document).ready(function() {
  $(window).on("scroll", function(e) {
    e.preventDefault();
    var scroll = $(window).scrollTop(),
      header = $("#header");

    if (scroll > 0) {
      header.addClass("active");
    } else {
      header.removeClass("active");
    }
  });
});
