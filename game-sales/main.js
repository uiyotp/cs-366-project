//function to switch tabs
function openPage(pageName) {
    var tabContent = document.getElementsByClassName("tabContent");
    for (var i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }
    document.getElementById(pageName).style.display = "block";
}

//event listener to handle all the form submissions
document.addEventListener("submit", (e) => {
	const form = e.target;
  
	fetch(form.action, {
	  method: form.method,
	  body: new FormData(form),
	}).then((res) => {
		if (!res.ok) {
			throw new Error('Response was not ok.');
		}
		const apiResponse = res;
		console.log(res);
		if (apiResponse.length > 0) {
			performExpectedAction(form.id, apiResponse);
		}else{
			console.error("empty");
			performErrorAction(form.id, "Empty");
		}
	  }).catch(error => {
		console.error(error);
		performErrorAction(form.id, "Error");
	  });
  
	e.preventDefault();
});

//gets the list of all genres for the top games tab
function getGenre() {
	getData(`gameSalesAPI.java?type=genre`).then(response => {
		const apiResponse = JSON.parse(response)
		if (apiResponse.length > 0) {
			performExpectedAction("topGenre", apiResponse);
		}
	});
}

//changes the score field to reflect review type
function changeReviewType(){
	if(document.getElementById('ratingType').value == 'user'){
		document.getElementById('scoreLabel').innerHTML = 'Score (1.0-10.0):';
		document.getElementById('score').placeholder = '8.5';
	}else{
		document.getElementById('scoreLabel').innerHTML = 'Score (1-100):';
		document.getElementById('score').placeholder = '85';
	}
}

//used to send the GET request
function getData(url) {
	if (url) {
		const task = new Promise( function(resolve, reject) {
  
		const req = new XMLHttpRequest();
		req.open('GET', url);
		req.send();
		req.onload = function(){
			req.status === 200 ? resolve(req.response) : reject(Error(req.statusText));
		}
		req.onerror = function(e) { reject(Error(`Network Error: ${e}`));
		} 
	
		});
		return task;
	}
	return false;
}

//uses the returned POST/GET results to do whatever action we want to do with them (fill in a form, generate output, etc.)
function performExpectedAction(id, list) {
	let output = "";
	switch(id){
		case "topGameFilter":
			//will populate the table
			output = `<tr>
			<th>Name</th>
			<th>Publisher</th>
			<th>Developer</th>
			<th>Year</th>
			<th>Genre</th>
			<th>Platform</th>
			<th>Critic Score</th>
			<th>User Score</th>
			<th>Age Rating</th>
			</tr>`;
			for(let i in list ) {
				const game = list[i];
				output += `<tr><td>${game.name}</td><td>${game.publisher}</td><td>${game.developer}</td>
							<td>${game.year}</td><td>${game.genre}</td><td>${game.platform}</td>
							<td>${game.critic_score}</td><td>${game.user_score}</td><td>${game.age_rating}</td></tr>`;
			}
			document.getElementById('topGames').innerHTML = output;
			break;
		case "editGameSearch":
			//will fill in the form
			document.getElementById('editGame').style = '';
			document.getElementById('editGameSearch').style = 'display: none;';

			document.getElementById('gameName').value = list[i].name;
			document.getElementById('pubName').value = list[i].publisher;
			document.getElementById('devName').value = list[i].developer;
			document.getElementById('year').value = list[i].year;
			document.getElementById('genre').value = list[i].genre;
			document.getElementById('platform').value = list[i].platform;
			document.getElementById('ageRating').value = list[i].age_rating;
			break;
		case "searchGame":
			//will populate the table
			output = `<tr>
			<th>Name</th>
			<th>Publisher</th>
			<th>Developer</th>
			<th>Year</th>
			<th>Genre</th>
			<th>Platform</th>
			<th>Critic Score</th>
			<th>User Score</th>
			<th>Age Rating</th>
			</tr>`;
			for(let i in list ) {
				const game = list[i];
				output += `<tr><td>${game.name}</td><td>${game.publisher}</td><td>${game.developer}</td>
							<td>${game.year}</td><td>${game.genre}</td><td>${game.platform}</td>
							<td>${game.critic_score}</td><td>${game.user_score}</td><td>${game.age_rating}</td></tr>`;
			}
			document.getElementById('searchGames').innerHTML = output;
			break;
		case "topGenre":
			//will populate the dropdown
			let selected = "selected";
			for(let i in list ) {
				const game = list[i];
				output += `<option ${selected} value="${i}">${game.genre}</option>`;
				selected = '';
			}
			document.getElementById('topGenre').innerHTML = output;
			break;
		default:
			console.log("Unexpected form submitted.");
	}
}

//shows the proper error on page
function performErrorAction(id, type) {
	if(type == "Error"){
		switch(id){
			case "topGameFilter":
				document.getElementById('topGameError').style = '';
				break;
			case "editGameSearch":
				document.getElementById('editGameSearchError').style = '';
				break;
			case "editGame":
				document.getElementById('editGameError').style = '';
				break;
			case "reviewGame":
				document.getElementById('reviewGameError').style = '';
				break;
			case "searchGame":
				document.getElementById('searchGameError').style = '';
				break;
			case "topGenre":
				document.getElementById('topGameError').style = '';
				break;
			default:
				console.log("Unexpected form submitted.");
		}
	}else if(type == "Empty"){
		switch(id){
			case "topGameFilter":
				document.getElementById('topGameError').style = '';
				break;
			case "editGameSearch":
				document.getElementById('editGameSearchEmpty').style = '';
				break;
			case "editGame":
				document.getElementById('editGameEmpty').style = '';
				break;
			case "reviewGame":
				document.getElementById('reviewGameEmpty').style = '';
				break;
			case "searchGame":
				document.getElementById('searchGameEmpty').style = '';
				break;
			case "topGenre":
				document.getElementById('topGameError').style = '';
				break;
			default:
				console.log("Unexpected form submitted.");
		}
	}
}