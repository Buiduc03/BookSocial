const heartIcons = document.querySelectorAll('.fa-heart-o');
    heartIcons.forEach(icon => {
        icon.addEventListener('click', function () {
            if (icon.classList.contains('fa-heart')) {
                icon.classList.remove('fa-heart');
                icon.classList.add('fa-heart-o');
            } else {
                icon.classList.remove('fa-heart-o');
                icon.classList.add('fa-heart');
            }
        });
    });



function showForm() {
        var form = document.getElementById("wantToReadForm");
        form.style.display = "block";
        document.getElementById("overlay").style.display = "block";
    }

    function closeForm() {
        var form = document.getElementById("wantToReadForm");
        form.style.display = "none";
        document.getElementById("overlay").style.display = "none";
    }


function handleFollow() {
    var unFlButton = document.getElementById("unfollow");
    var flButton = document.getElementById("follow");

    unFlButton.style.display = "block";
    flButton.style.display = "none";

}