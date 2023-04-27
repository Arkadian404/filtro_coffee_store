$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteBillingModal #deleteOrderId").val(id);
  });
});
