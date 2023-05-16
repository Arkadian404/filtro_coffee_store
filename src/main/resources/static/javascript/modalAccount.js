$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteAccountModal #deleteAccountId").val(id);
  });

  $("table .edit").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $.ajax({
      type: "GET",
      url: `/api/account/find/${id}`,
      success: function (account) {
        $("#editAccountModal #saveAccountId").val(id);
        $("#editAccountModal #accountName").val(account.accountName);
        $("#editAccountModal #password").val(account.password);
        $("#editAccountModal #editStatus").val(account.status);
        $("#editAccountModal #editRole").val(account.role.id);
      },
    });
  });
});
