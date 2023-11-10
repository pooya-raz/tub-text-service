function downloadFile() {
    document.getElementById('loader').style.display = 'flex';
    document.getElementById('download-button').style.display = 'none';

    fetch('/entry')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.blob();
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'tub.docx';
            document.body.appendChild(a);
            a.click();

            document.getElementById('loader').style.display = 'none';
            document.getElementById('download-button').style.display = 'block';
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            document.getElementById('loader').style.display = 'none';
            document.getElementById('download-error').style.display = 'block';
        });
}