function downloadFile() {
    // Show loader
    document.getElementById('loader').style.display = 'flex';
    document.getElementById('download-button').style.display = 'none';

    // Fetch the file from "/entry" endpoint
    fetch('/entry')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.blob();
        })
        .then(blob => {
            // Create a link element and trigger a click to download the file
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'downloaded_file.docx';
            document.body.appendChild(a);
            a.click();

            // Hide loader after the file is downloaded
            document.getElementById('loader').style.display = 'none';
            document.getElementById('download-button').style.display = 'block';
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);

            // Hide loader in case of an error
            document.getElementById('loader').style.display = 'none';
        });
}