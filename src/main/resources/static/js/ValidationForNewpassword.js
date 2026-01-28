$(document).ready(function(){
   var isStrengthValid = false;
   var isMatchValid = false;

   $('#newPassword').focus(function(){
       $('#password-validation').slideDown();
   })
    $('#newPassword').blur(function(){
        $('#password-validation').slideUp();
    })

    $('#newPassword').on('input', function(){
        var password = $(this).val();

        var rules = {
            'length' : password.length >= 8,
            'uppercase': /[A-Z]/.test(password),
            'number': /[0-9]/.test(password),
            'special': /[!@#$%^&*()<>.,]/.test(password),
        }

        updateRule("#rule-length", rules.length);
        updateRule("#rule-uppercase", rules.uppercase);
        updateRule("#rule-number", rules.number);
        updateRule("#rule-special", rules.special);

        isStrengthValid = rules.length && rules.uppercase && rules.number && rules.special;

        checkMatch();
        toggleButton();
    });

   $("#confirmPassword").on('input', function(){
       checkMatch();
       toggleButton();
   })

    function updateRule(elementId, isValid){
       var element = $(elementId);
       var icon = element.find('i');

       if(isValid){
           element.addClass("valid").removeClass("invalid");
           icon.removeClass("fa-circle").addClass("fa-check-circle");
       }else{
           element.addClass("invalid").removeClass("valid");
           icon.removeClass("fa-check-circle").addClass("fa-circle");
       }
    }

    function checkMatch(){
       var pass1 = $('#newPassword').val();
       var pass2 = $('#confirmPassword').val();
       var feedback = $('#match-feedback');

       if(pass2.length > 0){
           feedback.show();
           if(pass1 === pass2){
               feedback.text("Password Match").removeClass('text-danger').addClass('text-success');
               isMatchValid = true;
           }else{
               feedback.text("Password do not match").addClass('text-danger').removeClass('text-success');
               isMatchValid = false;
           }
       }else{
           feedback.hide();
           isMatchValid = false;
       }
    }

    function toggleButton(){
        if (isStrengthValid && isMatchValid) {
            $('#submitBtn').addClass('active');
        } else {
            $('#submitBtn').removeClass('active');
        }
    }
});