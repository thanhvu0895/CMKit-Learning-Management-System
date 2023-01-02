<html lang="en">
  <head>
    <title>Class page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/class.css" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
      crossorigin="anonymous"
    />
    <script src="https://kit.fontawesome.com/2912a97a77.js" crossorigin="anonymous"></script>
  </head>
  <body>
    <nav class="navbar bg-light">
      <div class="container-fluid">
        <div class="d-flex align-items-center">
          <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="" />
          </div>
          <a href="student_home.html" class="navbar-brand">My Classes</a>
        </div>
        <div>
          <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">User</button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
              <a class="dropdown-item" href="#">
                <span><i class="fa-solid fa-gear"></i></span>
                Account Settings
              </a>
              <a class="dropdown-item" href="#">
                <span><i class="fa-solid fa-bell"></i></span>
                Notification Settings
              </a>
              <a class="dropdown-item" href="#">
                <span><i class="fa-solid fa-lock"></i></span>
                SSH Keys
              </a>
              <hr />
              <button class="dropdown-item" href="#">
                <span><i class="fa-solid fa-right-to-bracket"></i></span>
                Logout
              </button>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <section class="container justify-content-center">
      <div class="d-flex p-2 align-items-center mt-4 bg-light">
        <a href="classes.html">Classes</a>
        <p>/</p>
        <p>Backend Super Intensive Class 3: Winter 2022</p>
      </div>
      <div class="d-flex flex-column align-items-center mt-4">
        <h1>Backend Super Intensive</h1>
        <h4>Winter 2022</h4>
        <div class="progress" style="width: 100%">
          <div class="progress-bar" role="progressbar" aria-valuenow="9" aria-valuemin="0" aria-valuemax="9" style="width: 90%">9 Graded</div>
        </div>
      </div>

      <div style="outline: 1px solid lightgrey;" class="mt-4">
        <li class="list-group-item hoverable-thing" id="helpFiles">
		
          <script>
            directorybrowser0Toggled = true
            function fvToggleIconFordirectorybrowser0()
            {
              if(directorybrowser0Toggled)
            {
              directorybrowser0Toggled = false
              document.getElementById("fvIconFordirectorybrowser0").className = "glyphicon glyphicon-folder-close"
            }
            else
            {
              directorybrowser0Toggled = true
              document.getElementById("fvIconFordirectorybrowser0").className = "glyphicon glyphicon-folder-open"
            }
            }
          </script>
          
          <a data-toggle="collapse" href="#directorybrowser0" onclick="fvToggleIconFordirectorybrowser0()">
            <strong>
            <span class="glyphicon glyphicon-folder-open" aria-hidden="true" id="fvIconFordirectorybrowser0 helpFiles" ></span>
            Class Files
            <span class="caret"></span>
            </strong>
          </a>
          <div></div>
          </li>
          <div id="directorybrowser0" class="accordian-collapse collapse in">
            <li class="list-group-item hoverable-thing">
            |----
              <a target="_blank" href="https://www.cs.kzoo.edu/CSShared/HelpFiles/Kit/">
                <span class="glyphicon glyphicon-link" aria-alt="(Link)"></span> Kit Help Files 
  </a>						
              <div class="pull-right">
              
                
                <a target="_blank" href="#">
                  <span class="glyphicon glyphicon-globe" aria-hidden="true" style="display:inline;top:0"></span>
  </a>						  
                &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
              </div>
            
          </li>
            <li class="list-group-item hoverable-thing">
            |----
              <a title="View" target="_blank" href="#">
              <span class="glyphicon glyphicon-file" aria-alt="(File)"></span>
              LinksToReflectiveResponseTemplates.html
  </a>					  
              <div class="pull-right">
                <a title="View" target="_blank" href="/repos/641/view_file?dir%5B%5D=student&amp;dir%5B%5D=LinksToReflectiveResponseTemplates.html">
                  <span class="glyphicon glyphicon-eye-open" style="display:inline;top:0" aria-alt="View"></span>
  </a>						
              &nbsp; &nbsp; &nbsp; &nbsp;
              <a title="Download" href="/repos/641/download?dir%5B%5D=student&amp;dir%5B%5D=LinksToReflectiveResponseTemplates.html">
                <span class="glyphicon glyphicon-save" style="display:inline;top:0" aria-alt="Download"></span>
  </a>					    
              &nbsp; &nbsp; &nbsp; &nbsp;
              </div>
          </li>
      </div>
      
	</div>
      </div>

      <div class="mt-4">
        <h2>Assignments</h2>
        <table class="table table-hover">
          <thead>
            <tr>
              <th>Title</th>
              <th>Category</th>
              <th>Due</th>
              <th>Status</th>
              <th>Submit</th>
              <th style="width: 20%">Grade</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Algorithm #1</td>
              <td>Data Structures and Algorithms</td>

              <td>Friday, Sep 9 at 11:59PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 90%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 9.0/10.0 (90.00%) </span>
                </div>
              </td>

              <td>
                <div class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Past Submissions<span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="/submissions/38063">2022-09-09 16:06:50 -0500</a></li>
                  </ul>
                </div>
              </td>
            </tr>

            <tr>
              <td>Algorithm #2</td>
              <td>Data Structures and Algorithms</td>

              <td>Friday, Sep 16 at 11:59PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 100%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 10.0/10.0 (100.0%) </span>
                </div>
              </td>

              <td></td>
            </tr>

            <tr>
              <td>Movie cinema booking class diagram</td>
              <td>System design</td>

              <td>Friday, Sep 30 at 11:59PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 80%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 8.0/10.0 (80.0%) </span>
                </div>
              </td>

              <td></td>
            </tr>

            <tr>
              <td>Mock Interview - Java fundamentals</td>
              <td>Object-Oriented Programming Fundamentals</td>

              <td>Saturday, Oct 8 at 8:00PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 75%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 7.5/10.0 (75.0%) </span>
                </div>
              </td>

              <td></td>
            </tr>

            <tr>
              <td>Group Project #1: Proposal Presentation</td>
              <td>Personal Project</td>

              <td>Saturday, Oct 15 at 11:59PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 100%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 9.5/10.0 (95.0%) </span>
                </div>
              </td>

              <td></td>
            </tr>

            <tr>
              <td>Group Project #2: HTML, CSS-BOOTSTRAP</td>
              <td>Personal Project</td>

              <td>Saturday, Nov 12 at 11:59PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 90%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 9.0/10.0 (90.0%) </span>
                </div>
              </td>

              <td></td>
            </tr>

            <tr>
              <td>Group Project #3: Database, SQL</td>
              <td>Personal Project</td>

              <td>Saturday, Nov 26 at 11:59PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 90%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 9.0/10.0 (90.0%) </span>
                </div>
              </td>

              <td>
                <div class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Past Submissions<span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="/submissions/36365">2022-09-26 16:06:25 -0400</a></li>
                  </ul>
                </div>
              </td>
            </tr>

            <tr>
              <td>Group Project #4: Final Presentation</td>
              <td>Personal Project</td>

              <td>Saturday, Dec 17 at 07:00PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 98.5%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 19.7/20.0 (98.5%) </span>
                </div>
              </td>

              <td>
                <div class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Past Submissions<span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="/submissions/36344">2022-09-30 14:21:08 -0400</a></li>
                  </ul>
                </div>
              </td>
            </tr>

            <tr>
              <td>Personal Reflection</td>
              <td>Individual growth</td>

              <td>Friday, Dec 23 at 11:59PM</td>

              <td>
                <p style="color: blue">Graded</p>
              </td>
              <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/submissions">Feedback</a></td>
              <td>
                <div class="progress" style="position: relative; text-align: center">
                  <div class="progress-bar" id="singleGrade" role="progressbar" aria-hidden="true" style="width: 100%"></div>
                  <span style="position: absolute; left: 0; right: 0"> 9.0/10.0 (90.0%) </span>
                </div>
              </td>

              <td></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="d-flex flex-column align-items-center mt-4">
        <h3> Current Grade: 99.28% </h3>
        <h4> Grade Summary: </h4>
        <table class="table table-hover">
          <thead> 
            <tr>
            <th>Category</th>
            <th>Category Weight</th>
            <th>Points Earned</th>
            <th>Percent</th>
          </tr>
          </thead>
          <tbody>
            <tr>
              <td> Data Structures and Algorithms </td>
            <td> 10.0% </td>
              <td> 29.0/30.0 </td>
            <td> 96.67% </td>
            </tr>
            <tr>
              <td> System design </td>
            <td> 45.0% </td>
              <td> 16.0/16.0 </td>
            <td> 100.0% </td>
            </tr>
            <tr>
              <td> Object-Oriented Programming Fundamentals </td>
            <td> 45.0% </td>
              <td> 69.4/70.0 </td>
            <td> 99.14% </td>
            </tr>
            <tr>
              <td> Personal Project </td>
            <td> 45.0% </td>
              <td> 69.4/70.0 </td>
            <td> 99.14% </td>
            </tr>
            <tr>
              <td> Attendance </td>
            <td> 0.0% </td>
              <td> 0/0 </td>
            <td> 100.0% </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script
      src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
      integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
      integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
      crossorigin="anonymous"
    ></script>
  </body>
</html>