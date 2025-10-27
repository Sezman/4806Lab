document.addEventListener("DOMContentLoaded", () => {
  const addBuddyForm = document.querySelector("#addBuddyForm");
  const buddyList = document.querySelector("#buddyList");

  if (!addBuddyForm) {
    console.error("addBuddyForm not found in the DOM!");
    return;
  }

  const bookId = addBuddyForm.dataset.bookId;

  addBuddyForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const name = addBuddyForm.querySelector("input[name='name']").value;
    const phoneNumber = addBuddyForm.querySelector("input[name='phoneNumber']").value;

    console.log("Adding buddy:", name, phoneNumber, "to book", bookId);

    try {
      const response = await fetch(`/addressbooks/${bookId}/buddies`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, phoneNumber })
      });

      if (!response.ok) {
        throw new Error("Server error: " + response.status);
      }

      const updatedBook = await response.json();
      console.log("Updated address book:", updatedBook);

      // Clear form fields
      addBuddyForm.reset();

      // Refresh buddy list
      renderBuddyList(updatedBook.buddies);
    } catch (err) {
      console.error("Error adding buddy:", err);
    }
  });

  function renderBuddyList(buddies) {
    buddyList.innerHTML = "";
    buddies.forEach(b => {
      const li = document.createElement("li");
      li.textContent = `${b.name} - ${b.phoneNumber}`;
      buddyList.appendChild(li);
    });
  }
});
