Running this project includes 2 phases.
	1. Running the USSD_Service up.
	2. Testing USSD codes using Africa's Talking.

1. Running the USSD_Service up.
	Requirements: Maven
	a. Go to the root directory to of project where pom.xml is located.
	b. Run "mvn sprin-boot:run"
	c. The springboot server will be started on port 8080.
	d. Expose the 8080 port to public internet using tool like "ngrok".
2. Testing USSD codes using Africa's Talking.
	a. Go to "https://africastalking.com/" and login.
	b. Click on "Go to Sandbox App'
	c. On the Sandbox app dashboard, go to "Service codes" under USSD section on left panel.
	d. Create a channel for our USSD code by choosing a service code, channel (not used), and ngrok link to be entered in "Callback URL".
	e. Launch simulator and choose a phone number to test. 
	f. Dial the USSD code created in above steps. 

Note: While testing USSD codes, you may experience errors so just in case relaunch the simulator and start again.

Team members:
- Arjun Dutta, MT2021022, arjun.dutta@iiitb.ac.in
- Arugonda Keerthi Sree, MT2021024, keerthi.sree@iiitb.ac.in
- Kunal Sharma, MT2021070, kunal.sharma@iiitb.ac.in
- Navneet Singh, MT2021081, navneet.singh@iiitb.ac.in
- Nikhil Mittal, MT2021083, nikhil.mittal@iiitb.ac.in
- Umang Okate, MT2021148, umang.okate@iiitb.ac.in