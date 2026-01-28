$(document).ready(function () {
    $("#receiverId").on("blur", function () {
        let id = $(this).val();
        let feedback = $("#account-checker-feedback");
        let input = $(this);
        if(id === "") {return}
        $.ajax({
            url: "/receiver_account_checker",
            type: "GET",
            data: {id: id},
            success: function (result) {
                if (!result.includes("not found.")) {
                    input.addClass("is-valid").removeClass("is-invalid");
                    feedback.text("Username : " + result).addClass('text-success').removeClass('text-danger');
                    // $("#submitBtn").prop('disabled', true);
                }else{
                    input.addClass('is-invalid').removeClass("is-valid");
                    feedback.text(result).addClass('text-danger').removeClass('text-success');
                }
            },
            error: function () {console.log("Error.")}
        })
    })
})