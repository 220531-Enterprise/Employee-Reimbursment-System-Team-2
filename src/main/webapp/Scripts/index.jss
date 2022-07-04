const uploadImage = document.querySelector('.upload')


uploadImage.addEventListener('submit', function(e) {
   e.preventDefault()
   let file = e.target.uploadFile.files[0]
   let formData = new FormData()
   formData.append('file', file)
})