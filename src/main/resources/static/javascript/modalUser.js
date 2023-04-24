$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteUserModal #deleteUserId").val(id);
  });

  $("table .edit").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $.ajax({
      type: "GET",
      url: `/api/user/find/${id}`,
      success: function (user) {
        $("#editUserModal #saveUserId").val(id);
        $("#editUserModal #name").val(user.name);
        $("#editUserModal #email").val(user.email);
        $("#editUserModal #phoneNumber").val(user.phoneNumber);
        $("#editUserModal #city").val(user.city);
        $("#editUserModal #zip").val(user.zip);
        $("#editUserModal #address").val(user.address);
        $("#editUserModal #sex").val(user.sex);
        $("#editUserModal #dob").val(user.dob);
        $("#editUserModal #account").val(user.account.id);
      },
    });
  });
});
