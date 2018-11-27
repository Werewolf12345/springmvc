function getuserdata(id) {
    $.ajax({
        url: "http://localhost:8081/user/"+id
    }).then(function(data) {
        $('.user-id').append(data.id);
        $('.user-firstname').append(data.firstName);
        $('.user-lastname').append(data.lastName);
        $('.user-email').append(data.email);
    });
}
