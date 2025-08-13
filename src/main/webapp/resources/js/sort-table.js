document.addEventListener('DOMContentLoaded', function() {
    const table = document.querySelector('.sortable');
    if (!table) return;

    const headers = table.querySelectorAll('th[data-sort]');
    headers.forEach(header => {
        header.addEventListener('click', () => {
            const sortType = header.getAttribute('data-sort');
            const columnIndex = Array.from(header.parentElement.children).indexOf(header);
            const isAscending = header.classList.contains('sorted-asc');

            // Reset all headers
            headers.forEach(h => {
                h.classList.remove('sorted-asc', 'sorted-desc');
            });

            // Set new sort direction
            header.classList.add(isAscending ? 'sorted-desc' : 'sorted-asc');

            // Sort table
            sortTable(table, columnIndex, sortType, isAscending);
        });
    });
});

function sortTable(table, columnIndex, sortType, isAscending) {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));

    rows.sort((a, b) => {
        const aValue = a.children[columnIndex].textContent.trim();
        const bValue = b.children[columnIndex].textContent.trim();

        switch(sortType) {
            case 'int':
                return (parseInt(aValue) - parseInt(bValue)) * (isAscending ? 1 : -1);
            case 'float':
                return (parseFloat(aValue.replace(/[^0-9.-]+/g,"")) -
                    parseFloat(bValue.replace(/[^0-9.-]+/g,""))) * (isAscending ? 1 : -1);
            case 'date':
                return (new Date(aValue) - new Date(bValue)) * (isAscending ? 1 : -1);
            default: // string
                return aValue.localeCompare(bValue) * (isAscending ? 1 : -1);
        }
    });

    // Rebuild table
    rows.forEach(row => tbody.appendChild(row));
}