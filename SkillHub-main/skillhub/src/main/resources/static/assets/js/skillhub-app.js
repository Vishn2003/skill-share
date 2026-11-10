/* SkillHub frontend: integrated with the actual REST controllers in this project. */
(() => {
  "use strict";
  const API = "";
  const page = location.pathname.split("/").pop() || "index.html";
  const userKey = "skillhub.currentStudent";
  const currentUser = () => { try { return JSON.parse(localStorage.getItem(userKey) || "null"); } catch { return null; } };
  const saveUser = user => localStorage.setItem(userKey, JSON.stringify(user));
  const esc = value => String(value ?? "").replace(/[&<>"']/g, c => ({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
  async function api(path, options={}) {
    const res = await fetch(API + path, { ...options, headers: { "Content-Type":"application/json", ...(options.headers||{}) }});
    const raw = await res.text(); let body = null;
    try { body = raw ? JSON.parse(raw) : null; } catch { body = raw; }
    if (!res.ok) throw new Error((body && body.message) || (typeof body==="string" && body) || `HTTP ${res.status}`);
    return body;
  }
  const get = path => api(path);
  const post = (path,data) => api(path,{method:"POST",body:JSON.stringify(data)});
  const put = (path,data) => api(path,{method:"PUT",body:JSON.stringify(data)});
  const del = path => api(path,{method:"DELETE"});
  function toast(message, isError=false) {
    let el=document.getElementById("sh-toast");
    if(!el){el=document.createElement("div");el.id="sh-toast";el.className="alert sh-toast shadow";document.body.appendChild(el);}
    el.className=`alert sh-toast shadow ${isError?"alert-danger":"alert-success"}`;el.textContent=message;
    clearTimeout(window.__shToastTimer);window.__shToastTimer=setTimeout(()=>el.remove(),4200);
  }
  function requireUser(){const u=currentUser();if(!u){location.href="login.html";return null;}return u;}
  function liveArea(){
    let el=document.getElementById("sh-live-area");
    if(!el){el=document.createElement("section");el.id="sh-live-area";el.className="sh-live-area";const main=document.querySelector("main");(main||document.body).appendChild(el);}
    return el;
  }
  function panel(title,body){return `<section class="sh-panel"><h2 class="h4 mb-3">${title}</h2>${body}</section>`;}
  function table(headers,rows){return `<div class="sh-table-wrap"><table class="table table-hover align-middle sh-table"><thead><tr>${headers.map(h=>`<th>${h}</th>`).join("")}</tr></thead><tbody>${rows||`<tr><td colspan="${headers.length}"><div class="sh-empty">No records found yet.</div></td></tr>`}</tbody></table></div>`;}
  const navFiles={"Dashboard":"dashboard.html","Explore Skills":"explore-skills.html","My Skills":"my-skills.html","Learning Requests":"requests.html","Learning Sessions":"sessions.html","Ratings & Reviews":"reviews.html","My Profile":"profile.html","Log In":"login.html","Get Started":"register.html","Sign In":"login.html","Register with your .edu email":"register.html","Home":"index.html"};
  function wireNavigation(){
    document.querySelectorAll("a").forEach(a=>{
      const label=(a.innerText||"").replace(/\s+/g," ").trim();
      for(const [key,file] of Object.entries(navFiles)){if(label===key || label.startsWith(key+" ")){a.setAttribute("href",file);break;}}
    });
    document.querySelectorAll("a,button").forEach(el=>{
      if((el.innerText||"").trim().toLowerCase()==="logout"){el.addEventListener("click",e=>{e.preventDefault();localStorage.removeItem(userKey);location.href="index.html";});}
    });
  }
  async function loginPage(){
    const form=document.querySelector("form");
    if(!form)return;
    form.removeAttribute("onsubmit");
    form.addEventListener("submit",async e=>{
      e.preventDefault();
      const email=document.querySelector("#student-email")?.value.trim();
      const password=document.querySelector("#student-password")?.value;
      try{
        const student=await post("/students/login",{email,password});
        if(!student){toast("Invalid email or password.",true);return;}
        saveUser(student);toast("Login successful. Welcome to SkillHub!");
        setTimeout(()=>location.href="dashboard.html",500);
      }catch(err){toast(err.message,true);}
    });
  }
  async function registerPage(){
    const form=document.querySelector("#registrationForm");if(!form)return;
    form.removeAttribute("onsubmit");
    const academic=document.querySelector("#academicStatus");
    const parent=academic?.parentElement;
    if(parent){
      parent.insertAdjacentHTML("afterend",`<div class="row g-3"><div class="col-md-8 sh-field"><label for="sh-department" class="form-label">Department</label><input id="sh-department" class="form-control" placeholder="Computer Science" required></div><div class="col-md-4 sh-field"><label for="sh-year" class="form-label">Year</label><select id="sh-year" class="form-select" required><option value="">Select year</option><option value="1">1st</option><option value="2">2nd</option><option value="3">3rd</option><option value="4">4th</option><option value="5">5th</option></select></div></div>`);
    }
    form.addEventListener("submit",async e=>{
      e.preventDefault();
      const password=document.querySelector("#password")?.value;
      const confirm=document.querySelector("#confirmPassword")?.value;
      if(password!==confirm){toast("Passwords do not match.",true);return;}
      const first=document.querySelector("#firstName")?.value.trim()||"";
      const last=document.querySelector("#lastName")?.value.trim()||"";
      const department=document.querySelector("#sh-department")?.value.trim() || academic?.value.trim() || "";
      const year=Number(document.querySelector("#sh-year")?.value || 1);
      const payload={name:`${first} ${last}`.trim(),email:document.querySelector("#email")?.value.trim(),password,department,year};
      try{await post("/students/register",payload);toast("Account created. Please log in.");setTimeout(()=>location.href="login.html",700);}
      catch(err){toast(err.message,true);}
    });
  }
  async function mountDashboard(){
    const u=requireUser();if(!u)return;
    const area=liveArea();
    try{
      const [skills,requests,myskills]=await Promise.all([get("/skills"),get(`/requests/learner/${u.studentId}`),get(`/student-skills/student/${u.studentId}`)]);
      const incoming=await get(`/requests/mentor/${u.studentId}`);
      area.innerHTML=panel(`Welcome back, ${esc(u.name)}`,`<p class="sh-muted">Your SkillHub activity, loaded from your Spring Boot backend.</p><div class="row g-3"><div class="col-md-3"><div class="p-3 bg-light rounded-3"><div class="sh-muted">Available skills</div><div class="display-6 fw-bold">${skills.length}</div></div></div><div class="col-md-3"><div class="p-3 bg-light rounded-3"><div class="sh-muted">My offerings</div><div class="display-6 fw-bold">${myskills.length}</div></div></div><div class="col-md-3"><div class="p-3 bg-light rounded-3"><div class="sh-muted">Requests sent</div><div class="display-6 fw-bold">${requests.length}</div></div></div><div class="col-md-3"><div class="p-3 bg-light rounded-3"><div class="sh-muted">Requests received</div><div class="display-6 fw-bold">${incoming.length}</div></div></div></div>`) +
      panel("Recent learning requests",table(["Request","Skill ID","Status","Message"],[...requests,...incoming].slice(0,6).map(r=>`<tr><td>#${r.requestId}</td><td>${r.skillId}</td><td><span class="badge text-bg-${r.status==="ACCEPTED"?"success":r.status==="REJECTED"?"danger":"secondary"}">${esc(r.status)}</span></td><td>${esc(r.message||"—")}</td></tr>`).join("")));
    }catch(err){area.innerHTML=panel("Dashboard",`<div class="alert alert-warning">${esc(err.message)}</div>`);}
  }
  async function mountExplore(){
    const u=requireUser();if(!u)return;const area=liveArea();
    try{
      const [skills,studentSkills,students]=await Promise.all([get("/skills"),get("/student-skills"),get("/students")]);
      const mentorSkills=studentSkills.filter(ss=>ss.student?.studentId!==u.studentId);
      const search= document.querySelector("#main-skill-search");
      const render=(term="")=>{
        const matches=skills.filter(s=>(s.skillName||"").toLowerCase().includes(term.toLowerCase())||(s.category||"").toLowerCase().includes(term.toLowerCase()));
        const rows=matches.map(s=>{
          const providers=mentorSkills.filter(ss=>ss.skill?.skillId===s.skillId);
          if(!providers.length)return `<tr><td>${esc(s.skillName)}</td><td>${esc(s.category)}</td><td class="sh-muted">No student mentor listed yet</td><td>—</td></tr>`;
          return providers.map(p=>`<tr><td>${esc(s.skillName)}</td><td>${esc(s.category)}</td><td>${esc(p.student?.name||"Student")} <span class="sh-muted">(${esc(p.student?.department||"")})</span></td><td><button class="btn btn-sm btn-primary sh-action" data-request-skill="${s.skillId}" data-mentor="${p.student.studentId}">Request to learn</button></td></tr>`).join("");
        }).join("");
        document.getElementById("sh-explore-table").innerHTML=table(["Skill","Category","Student mentor","Action"],rows);
      };
      area.innerHTML=panel("Live skill directory",`<p class="sh-muted">Skills and student mentors from your database. Select a mentor to send a learning request.</p><div class="mb-3"><input id="sh-search" class="form-control" placeholder="Search skills or categories..."></div><div id="sh-explore-table"></div>`);
      render();document.getElementById("sh-search").addEventListener("input",e=>render(e.target.value));
      area.addEventListener("click",async e=>{
        const btn=e.target.closest("[data-request-skill]");if(!btn)return;
        const message=prompt("Add a short message for the mentor (optional):")||"I would like to learn this skill.";
        try{await post("/requests",{learner:{studentId:u.studentId},mentor:{studentId:Number(btn.dataset.mentor)},skill:{skillId:Number(btn.dataset.requestSkill)},message});toast("Learning request sent.");}
        catch(err){toast(err.message,true);}
      });
    }catch(err){area.innerHTML=panel("Explore skills",`<div class="alert alert-danger">${esc(err.message)}</div>`);}
  }
  async function mountMySkills(){
    const u=requireUser();if(!u)return;
    const legacyForm=document.querySelector("main form");
    if(legacyForm){legacyForm.removeAttribute("onsubmit");legacyForm.style.display="none";}
    const area=liveArea();
    area.innerHTML=panel("Add a skill you can teach",`<form id="sh-skill-form" class="row g-3"><div class="col-md-5"><label class="form-label">Skill name</label><input name="skillName" class="form-control" required placeholder="Java, UI Design..."></div><div class="col-md-4"><label class="form-label">Category</label><input name="category" class="form-control" required placeholder="Programming, Design..."></div><div class="col-md-3"><label class="form-label">Level</label><select name="level" class="form-select"><option>Beginner</option><option selected>Intermediate</option><option>Advanced</option></select></div><div class="col-12"><button class="btn btn-primary">Publish skill</button></div></form>`) + panel("My published skills",`<div id="sh-my-skills"></div>`);
    async function refresh(){
      const list=await get(`/student-skills/student/${u.studentId}`);
      document.getElementById("sh-my-skills").innerHTML=table(["Skill","Category","Level","Action"],list.map(x=>`<tr><td>${esc(x.skill?.skillName)}</td><td>${esc(x.skill?.category)}</td><td>${esc(x.level)}</td><td><button class="btn btn-sm btn-outline-danger" data-remove-skill="${x.id}">Remove</button></td></tr>`).join(""));
    }
    await refresh();
    document.getElementById("sh-skill-form").addEventListener("submit",async e=>{
      e.preventDefault();const fd=new FormData(e.target);const name=String(fd.get("skillName")).trim(),category=String(fd.get("category")).trim();
      try{
        const all=await get("/skills");let skill=all.find(s=>s.skillName.toLowerCase()===name.toLowerCase());
        if(!skill)skill=await post("/skills",{skillName:name,category});
        await post("/student-skills",{student:{studentId:u.studentId},skill:{skillId:skill.skillId},level:fd.get("level")});
        e.target.reset();await refresh();toast("Your skill has been published.");
      }catch(err){toast(err.message,true);}
    });
    area.addEventListener("click",async e=>{const b=e.target.closest("[data-remove-skill]");if(!b)return;try{await del(`/student-skills/${b.dataset.removeSkill}`);await refresh();toast("Skill removed.");}catch(err){toast(err.message,true);}});
  }
  async function mountRequests(){
    const u=requireUser();if(!u)return;const area=liveArea();
    try{
      const [sent,received,skills,students]=await Promise.all([get(`/requests/learner/${u.studentId}`),get(`/requests/mentor/${u.studentId}`),get("/skills"),get("/students")]);
      const skillMap=Object.fromEntries(skills.map(s=>[s.skillId,s.skillName]));
      const studentMap=Object.fromEntries(students.map(s=>[s.studentId,s.name]));
      const sentRows=sent.map(r=>`<tr><td>#${r.requestId}</td><td>${esc(skillMap[r.skillId]||r.skillId)}</td><td>${esc(studentMap[r.mentorId]||r.mentorId)}</td><td>${esc(r.status)}</td><td>${esc(r.message||"")}</td></tr>`).join("");
      const receivedRows=received.map(r=>`<tr><td>#${r.requestId}</td><td>${esc(skillMap[r.skillId]||r.skillId)}</td><td>${esc(studentMap[r.learnerId]||r.learnerId)}</td><td>${esc(r.status)}</td><td>${r.status==="PENDING"?`<button class="btn btn-sm btn-success me-1" data-status="${r.requestId}" data-value="ACCEPTED">Accept</button><button class="btn btn-sm btn-outline-danger" data-status="${r.requestId}" data-value="REJECTED">Reject</button>`:"—"}</td></tr>`).join("");
      area.innerHTML=panel("Requests I've sent",table(["ID","Skill","Mentor","Status","Message"],sentRows))+panel("Requests I've received",table(["ID","Skill","Learner","Status","Action"],receivedRows));
      area.addEventListener("click",async e=>{const b=e.target.closest("[data-status]");if(!b)return;try{await api(`/requests/${b.dataset.status}/status?status=${b.dataset.value}`,{method:"PUT"});toast(`Request ${b.dataset.value.toLowerCase()}.`);mountRequests();}catch(err){toast(err.message,true);}});
    }catch(err){area.innerHTML=panel("Learning requests",`<div class="alert alert-danger">${esc(err.message)}</div>`);}
  }
  async function mountProfile(){
    const u=requireUser();if(!u)return;const area=liveArea();
    try{
      const student=await get(`/students/${u.studentId}`);
      area.innerHTML=panel("Edit student profile",`<form id="sh-profile-form" class="row g-3"><div class="col-md-6"><label class="form-label">Full name</label><input class="form-control" name="name" required value="${esc(student.name)}"></div><div class="col-md-6"><label class="form-label">Email</label><input class="form-control" name="email" type="email" required value="${esc(student.email)}"></div><div class="col-md-6"><label class="form-label">Department</label><input class="form-control" name="department" required value="${esc(student.department)}"></div><div class="col-md-3"><label class="form-label">Year</label><input class="form-control" name="year" type="number" min="1" max="5" required value="${esc(student.year)}"></div><div class="col-12"><button class="btn btn-primary">Save profile</button></div></form>`);
      document.getElementById("sh-profile-form").addEventListener("submit",async e=>{e.preventDefault();const f=new FormData(e.target);const payload={name:f.get("name"),email:f.get("email"),department:f.get("department"),year:Number(f.get("year"))};try{const updated=await put(`/students/${u.studentId}`,payload);saveUser(updated);toast("Profile updated.");}catch(err){toast(err.message,true);}});
    }catch(err){area.innerHTML=panel("Profile",`<div class="alert alert-danger">${esc(err.message)}</div>`);}
  }
  async function mountReviews(){
    const u=requireUser();if(!u)return;const area=liveArea();
    try{
      const [ratings,students,requests]=await Promise.all([get("/ratings"),get("/students"),get(`/requests/learner/${u.studentId}`)]);
      const names=Object.fromEntries(students.map(s=>[s.studentId,s.name]));
      const rows=ratings.map(r=>`<tr><td>${esc(names[r.learner?.studentId]||"Learner")}</td><td>${esc(names[r.mentor?.studentId]||"Mentor")}</td><td>${"★".repeat(Math.max(0,Math.min(5,r.rating||0)))}</td><td>${esc(r.feedback||"")}</td></tr>`).join("");
      area.innerHTML=panel("Community reviews",table(["Learner","Mentor","Rating","Feedback"],rows))+panel("Leave a review",`<form id="sh-review-form" class="row g-3"><div class="col-md-6"><label class="form-label">Completed request</label><select class="form-select" name="requestId" required><option value="">Select request</option>${requests.filter(r=>r.status==="COMPLETED").map(r=>`<option value="${r.requestId}" data-mentor="${r.mentorId}">Request #${r.requestId} — Skill #${r.skillId}</option>`).join("")}</select></div><div class="col-md-3"><label class="form-label">Rating</label><select class="form-select" name="rating"><option value="5">5 - Excellent</option><option value="4">4 - Very good</option><option value="3">3 - Good</option><option value="2">2 - Fair</option><option value="1">1 - Poor</option></select></div><div class="col-12"><label class="form-label">Feedback</label><textarea class="form-control" name="feedback" maxlength="500" required></textarea></div><div class="col-12"><button class="btn btn-primary">Submit review</button></div></form>`);
      document.getElementById("sh-review-form").addEventListener("submit",async e=>{e.preventDefault();const f=new FormData(e.target);const opt=e.target.elements.requestId.selectedOptions[0];const reqId=Number(f.get("requestId"));const req=requests.find(r=>r.requestId===reqId);try{await post("/ratings",{mentor:{studentId:req.mentorId},learner:{studentId:u.studentId},request:{requestId:reqId},rating:Number(f.get("rating")),feedback:f.get("feedback")});toast("Review submitted.");mountReviews();}catch(err){toast(err.message,true);}});
    }catch(err){area.innerHTML=panel("Reviews",`<div class="alert alert-danger">${esc(err.message)}</div>`);}
  }
  async function mountSessions(){
    const u=requireUser();if(!u)return;const area=liveArea();
    try{
      const [sent,received,skills,students]=await Promise.all([get(`/requests/learner/${u.studentId}`),get(`/requests/mentor/${u.studentId}`),get("/skills"),get("/students")]);
      const sm=Object.fromEntries(skills.map(s=>[s.skillId,s.skillName])),nm=Object.fromEntries(students.map(s=>[s.studentId,s.name]));
      const all=[...sent.map(r=>({...r,role:"Learner"})),...received.map(r=>({...r,role:"Mentor"}))].filter(r=>["ACCEPTED","COMPLETED"].includes(r.status));
      const rows=all.map(r=>`<tr><td>#${r.requestId}</td><td>${esc(sm[r.skillId]||r.skillId)}</td><td>${esc(r.role==="Learner"?nm[r.mentorId]:nm[r.learnerId])}</td><td>${esc(r.role)}</td><td>${esc(r.status)}</td>${r.role==="Mentor"&&r.status==="ACCEPTED"?`<td><button class="btn btn-sm btn-success" data-complete="${r.requestId}">Mark completed</button></td>`:"<td>—</td>"}</tr>`).join("");
      area.innerHTML=panel("Learning sessions",`<p class="sh-muted">Your backend currently tracks accepted/completed learning requests, but does not have a separate session scheduling entity or date/time API.</p>${table(["Request","Skill","Peer","Your role","Status","Action"],rows)}`);
      area.addEventListener("click",async e=>{const b=e.target.closest("[data-complete]");if(!b)return;try{await api(`/requests/${b.dataset.complete}/status?status=COMPLETED`,{method:"PUT"});toast("Learning request marked completed.");mountSessions();}catch(err){toast(err.message,true);}});
    }catch(err){area.innerHTML=panel("Learning sessions",`<div class="alert alert-danger">${esc(err.message)}</div>`);}
  }
  async function landing(){
    // Landing page is public; route prominent CTAs to the actual app pages.
    wireNavigation();
  }
  document.addEventListener("DOMContentLoaded",()=>{
    wireNavigation();
    const routes={
      "login.html":loginPage,"register.html":registerPage,"dashboard.html":mountDashboard,
      "explore-skills.html":mountExplore,"my-skills.html":mountMySkills,"requests.html":mountRequests,
      "profile.html":mountProfile,"reviews.html":mountReviews,"sessions.html":mountSessions,
      "index.html":landing
    };
    (routes[page]||landing)().catch(err=>toast(err.message,true));
  });
})();
