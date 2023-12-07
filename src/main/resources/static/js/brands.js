const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content;
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content;
const brandsSection = document.querySelector('[name="brand-section"]');

fetch('/api/all-brands')
.then((response) => response.json())
.then((body) => {
    for(brand of body) {
        let brandHTML = '<div>';
        brandHTML += '<div hidden>' + brand.id + '</div>';
        brandHTML += '<a class="card-link text-decoration-none fw-bold h5 mt-4" href="/brand-products/' + brand.id + '">' + brand.name + '</a>';
        brandHTML += '</div>'

        brandsSection.innerHTML += brandHTML;
    }
})

