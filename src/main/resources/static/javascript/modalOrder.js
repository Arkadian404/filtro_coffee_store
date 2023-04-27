$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteOrderModal #deleteOrderId").val(id);
  });

  $("table .edit").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $.ajax({
      type: "GET",
      url: `/api/order/find/${id}`,
      success: function (order) {
        $("#editOrderModal #saveOrderId").val(id);
        $("#editOrderModal #user").val(order.user.id);
        $("#editOrderModal #orderDate").val(order.orderDate);
        $("#editOrderModal #email").val(order.email);
        $("#editOrderModal #phoneNumber").val(order.phoneNumber);
        $("#editOrderModal #address").val(order.address);
        $("#editOrderModal #city").val(order.city);
        $("#editOrderModal #zip").val(order.zip);
        $("#editOrderModal #paymentMethod").val(order.paymentMethod.name);
        $("#editOrderModal #total").val(order.total);
        $("#editOrderModal #status").val(order.status);
      },
    });
  });
});
