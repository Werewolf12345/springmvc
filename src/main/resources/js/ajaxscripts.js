function getuserdata(url) {
    $.ajax({
        url: url
    }).then(function(data) {
        $('.user-id').append(data.id);
        $('.user-firstname').append(data.firstName);
        $('.user-lastname').append(data.lastName);
        $('.user-email').append(data.email);
    });
}

function userstable() {
    $.ajax({
        url: '/users',
        success(response) {
            var trHTML = '';
            $.each(response, function (i, item) {
                trHTML += '<tr><td>' + item.id + '</td><td>' + item.firstName + '</td><td>' + item.lastName + '</td><td>' + item.email + '</td></tr>';
            });
            $('#userstable').append(trHTML);
        }
    });
}

