%% ************ AKADEMIA GÓRNICZO-HUTNICZA W KRAKOWIE **************
%% *************** PRACA MAGISTERSKA w LaTeX-u *********************
%%   To jest klasa mgragh --- PRACA MAGISTERSKA w LaTeX-u
%%   
%%   Klasa pochodna od klasy Marcina Wolinskiego 'mwbk'
%%   z przerobkami dokonanymi na potrzeby pracy magisterskiej
%%   
%%   version 0.55a, date: 10/04/2003 
%%   
%%   Copyright (C) 2003 by Rafa³ Czarny & WMS
%%   
%-------------- Naglowek klasy ----------------%  
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{mgragh}
 [10/04/2003 v0.55a]
  \typeout{************************************************}
  \typeout{** AKADEMIA GORNICZO-HUTNICZA W KRAKOWIE        }
  \typeout{** Praca magisterska w LaTeX-u                  }
  \typeout{** Klasa mgragh.cls --- v. 0.55a 10/04/2003     }
  \typeout{** Autor: Rafa/l Czarny                         }
  \typeout{************************************************}
\DeclareOption*{%
 \PassOptionsToClass{\CurrentOption}{mwbk}}
%---------------- nowe if-y -------------------%
\newif\ifmgr@pdftex\mgr@pdftexfalse
\newif\ifmgr@man\mgr@manfalse
\newif\ifmgr@draft\mgr@draftfalse
%----------------- Opcje ----------------------%
\DeclareOption{pdftex}{\mgr@pdftextrue}
\DeclareOption{robocza}{\mgr@drafttrue}
\DeclareOption{man}{\mgr@mantrue}
\ProcessOptions
%---------- Zaladowanie pakietow ---------------%
\LoadClass[12pt,a4paper,oneside]{mwbk}
\RequirePackage{fancyhdr}
\RequirePackage{calc}
\RequirePackage{ifthen}
\RequirePackage{makeidx}
\ifmgr@pdftex
 \RequirePackage[pdftex]{graphicx}
\else
 \RequirePackage[dvips]{graphicx}\fi
%----- Definicje uniwersalnych literek :)-------%
\newcommand{\m@a}{\char161}
\newcommand{\m@c}{\char162}
\newcommand{\m@e}{\char166}
\newcommand{\m@l}{\char170}
\newcommand{\m@o}{\char243}
\newcommand{\m@s}{\char177}
\newcommand{\m@z}{\char187}
\ifmgr@man\gdef\m@k{y}\else\gdef\m@k{a}\fi
%---------------- Marginesy --------------------%
\newlength{\mgrlewy}\newlength{\mgrprawy}
\newlength{\mgrgora}\newlength{\mgrdol}
\newlength{\mgrstopka}\newlength{\mgrdoglowki}
%------- Ustawienia marginesów --------%
\setlength{\mgrlewy}{3.5cm}
\setlength{\mgrprawy}{2.1cm}
\setlength{\mgrgora}{1.75cm}
\setlength{\mgrdol}{2.25cm}
\setlength{\mgrstopka}{2.75\baselineskip}
\setlength{\mgrdoglowki}{1.5pt}
%--------------------------------------%
\newlength{\@pomc}
\newcount\@tempcntr
\setlength\marginparwidth{0pt}
\setlength\marginparsep{0pt}
\setlength\oddsidemargin{\mgrlewy}
\addtolength\oddsidemargin{-1in} 
\setlength\evensidemargin{\mgrprawy}
\addtolength\evensidemargin{-1in} 
\setlength\textwidth{\paperwidth}
\addtolength\textwidth{-\mgrlewy}
\addtolength\textwidth{-\mgrprawy}
  %------ wymiary pionowe -------%
\setlength\headheight{\baselineskip}
\addtolength\headheight{\headrulewidth}
\setlength\headsep{\baselineskip}
\addtolength\headsep{\mgrdoglowki}
\setlength\footskip{\mgrstopka}
\setlength\topmargin{\mgrgora}
\addtolength\topmargin{-1in}
\setlength\@pomc{\paperheight}
\addtolength\@pomc{-\mgrgora}
\addtolength\@pomc{-\mgrdol}
\addtolength\@pomc{-\headheight}
\addtolength\@pomc{-\headsep}
\addtolength\@pomc{-\footskip}
 \advance\@pomc-.5\baselineskip
 \divide\@pomc\baselineskip
 \@tempcntr=\@pomc
 \setlength\textheight{\@tempcntr\baselineskip}
 \addtolength\textheight{\topskip} 
%% Kontrolujemy odstepy pionowe
\raggedbottom
\def\cleardoublepage{\clearpage}
%-------------- Odstepy w tytulariach ----------------%
\newskip\sec@bfrskip\newskip\sec@aftskip
\newskip\subsec@bfrskip\newskip\subsec@aftskip
\newskip\subsubsec@bfrskip\newskip\subsubsec@aftskip
%%
\sec@bfrskip=2.75ex plus.3ex minus.2ex\sec@aftskip=1.5ex plus.2ex
\subsec@bfrskip=2.25ex plus.2ex minus.1ex \subsec@aftskip=1.2ex plus.15ex
\subsubsec@bfrskip=1.75ex plus.1ex minus.1ex \subsubsec@aftskip=.85ex plus.1ex
%-------------- Definicje fontow tytulariow ----------------%
\newcommand{\part@number}{\LARGE\bfseries} 
\newcommand{\part@name}{\huge\bfseries}    
\newcommand{\chap@number}{\Large\bfseries}
\newcommand{\chap@name}{\LARGE\bfseries}
\newcommand{\sec@format}{\large}
\newcommand{\subsec@format}{\normalsize}
\newcommand{\subsubsec@format}{}
%-------------- Sklad tytulariow ----------------%
\newcounter{mgr@chapter@page} 
\def\hrulefilli#1{\leavevmode\leaders\hrule height#1\hfill\kern\z@}
%--------- Sklad poszczegolnych naglowkow ---------%
%---- sk³ad dla part ----%  
\newcommand{\mgr@choose@part@side}
 {\setcounter{mgr@chapter@page}{\value{page}}
  \if@twoside
   \ifthenelse{\isodd{\value{mgr@chapter@page}}}
	 {\def\mgr@part@side{\raggedleft}\def\mgr@part@left{}\def\mgr@part@right{\hfill}}
	 {\def\mgr@part@side{\raggedright}\def\mgr@part@left{\hfill}\def\mgr@part@right{}}
	\else\def\mgr@part@side{\raggedleft}\def\mgr@part@left{}\def\mgr@part@right{\hfill}\fi 
 }
\newcommand{\MgrFormatPartHeading}[3]{%
  \mgr@choose@part@side%
  \mgr@part@left\parbox{\textwidth-3em}{%
	\mgr@part@side
	\ifHeadingNumbered 
	  \noindent#2\normalfont\part@number\partname\space
		 \HeadingNumber\ignorespaces \\[\smallskipamount]
		\noindent\rule{.25\textwidth}{5pt}	
	 \par\vspace{\baselineskip}
	\else\fi
	 #3\normalfont\part@name\HeadingText}\mgr@part@right
}
\DeclareSectioningCommand[breakbefore,breakafter,wholewidth]{part}{-1} % tez nowe
  {0\p@ plus1fil}
  {\MgrFormatPartHeading{}{}{}}
  {0\p@ plus1fil}
%---- sk³ad dla chapter ----%
\renewcommand{\FormatChapterHeading}[3]{% 
  \ifHeadingNumbered #2%
	\begin{center}
   \begin{tabular}{@{}c@{}}
	   \raisebox{.5ex}{\rule{.25\textwidth}{.5pt}}\hspace{.5em}% % \chaptername --- \@chapapp
     \normalfont\chap@number\@chapapp\space\HeadingNumber\ignorespaces%
     \hspace{.5em}\raisebox{.5ex}{\rule{.25\textwidth}{.5pt}} \\
     \hrulefilli{.5pt}
  \end{tabular}\end{center}\par\nobreak%
  \vspace{3\baselineskip}\fi%
  #3\normalfont\chap@name\begin{center}\HeadingText\end{center}\par\nobreak%
	\setcounter{mgr@chapter@page}{\value{page}} 
  \thispagestyle{mgropen}%
}
\DeclareSectioningCommand[breakbefore,wholewidth]{chapter}{0} 
  {56pt}            
  {\FormatChapterHeading{}{}{}}
  {6\baselineskip}  
%---- sk³ad dla section ----%
\DeclareSectioningCommand{section}{1}
  {\sec@bfrskip}
   {\sec@format\ifHeadingNumbered
    \HeadingNumber.\enspace\fi%
    \protect\parbox[t]{\textwidth-\widthof{%
      \ifHeadingNumbered\sec@format\HeadingNumber.\enspace\fi}}{\raggedright\HeadingText}}
  {\sec@aftskip}
%---- sk³ad dla subsection ----%
\DeclareSectioningCommand{subsection}{2}
  {\subsec@bfrskip}
   {\subsec@format\ifHeadingNumbered
    \HeadingNumber.\enspace\fi%
    \protect\parbox[t]{\textwidth-\widthof{%
     \ifHeadingNumbered\subsec@format\HeadingNumber.\enspace\fi}}{\raggedright\HeadingText}}
   {\subsec@aftskip}
%---- sk³ad dla subsubsection ----%
\DeclareSectioningCommand{subsubsection}{3}
  {\subsubsec@bfrskip}
   {\subsubsec@format\ifHeadingNumbered
    \HeadingNumber.\enspace\fi%
    \protect\parbox[t]{\textwidth-\widthof{%
      \ifHeadingNumbered\subsubsec@format\HeadingNumber.\enspace\fi}}{\raggedright\HeadingText}}
   {\subsubsec@aftskip}
%---- sk³ad dla paragraph i subparagraph ----%
\DeclareSectioningCommand[runin]{paragraph}{4}
  {\bigskipamount}
  {\FormatRunInHeading{}}
  {1em}
\DeclareSectioningCommand[runin]{subparagraph}{5}
  {\medskipamount}
  {\FormatRunInHeading{}}
  {.5em}
%-------------- Naglowki stron, 'fancyhdr' ----------------%
%-------- Nag³ówek roboczy (opcja draft) ---------%
\newcommand{\mgr@hd@dft@style}{\footnotesize}
\newcommand{\mgr@ft@dft@style}{\small}
\newcommand{\mgr@aut@dft@style}{\bfseries}
\newcommand{\mgr@draft@set@time}  %% Zapo¿yczone z uiucthesis :) 
 {\newcount\mgr@timehh\newcount\mgr@timemm
  \mgr@timehh=\time\mgr@timemm=\time
	\divide\mgr@timehh by 60
  \count255=\mgr@timehh\multiply\count255 by -60
  \advance\mgr@timemm by\count255 
 }
\newcommand{\mgr@draft@datetime}
 {\ifnum\day<10 0\fi\number\day.%
  \ifnum\month<10 0\fi\number\month.\number\year,\
  \ifnum\mgr@timehh<10 0\fi\number\mgr@timehh:%
	\ifnum\mgr@timemm<10 0\fi\number\mgr@timemm%
 }
\ifmgr@draft\mgr@draft@set@time\else\fi
\newcommand{\mgr@head@draft@open}
 {\ifmgr@draft%
   \if@twoside
	  \fancyfoot[LO]{\mgr@ft@dft@style Autor: {\mgr@aut@dft@style\@author}}
	  \fancyfoot[RE]{\mgr@ft@dft@style Autor: {\mgr@aut@dft@style\@author}}
	 \else\lfoot{\mgr@ft@dft@style Autor: {\mgr@aut@dft@style\@author}}
	\fi\else\fi
 }
\newcommand{\mgr@head@draft}
 {\ifmgr@draft%
   \if@twoside
    \fancyhead[LO]{\texttt{\mgr@hd@dft@style Wersja robocza: \mgr@draft@datetime}}
	  \fancyhead[RE]{\texttt{\mgr@hd@dft@style Wersja robocza: \mgr@draft@datetime}}
		\fancyfoot[LO]{\mgr@ft@dft@style Autor: {\mgr@aut@dft@style\@author}}
		\fancyfoot[RE]{\mgr@ft@dft@style Autor: {\mgr@aut@dft@style\@author}}
	 \else
	  \lhead{\texttt{\mgr@hd@dft@style Wersja robocza: \mgr@draft@datetime}}
		\lfoot{\mgr@ft@dft@style Autor: {\mgr@aut@dft@style\@author}}
	 \fi\else\fi
 }
\newcommand{\mgr@head@style}{\small\sffamily}
\newcommand{\mgr@foot@style}{\sffamily}
\newlength{\mgr@head@lower}\setlength{\mgr@head@lower}{-1pt}
\newlength{\mgr@linia}\setlength{\mgr@linia}{0.3pt}
\pagestyle{fancy}
\newcommand{\mgr@def@head}{
\renewcommand{\chaptermark}[1]{\markboth{\mgr@head@style\chaptername\ \thechapter.\ ##1}{}}
\renewcommand{\sectionmark}[1]{\markright{\mgr@head@style\thesection.\ ##1}}}
\gdef\mgr@odd@pos{RO}\gdef\mgr@even@pos{LE}
\mgr@def@head
%-------- styl do stron normalnych ---------%
\fancypagestyle{mgrnormal}{%
 \fancyhf{} 
 \mgr@def@head
 \mgr@head@draft
 \renewcommand{\headrulewidth}{\mgr@linia}
 \if@twoside
  \fancyhead[\mgr@odd@pos]{\raisebox{\mgr@head@lower}{\rightmark}} 
  \fancyhead[\mgr@even@pos]{\raisebox{\mgr@head@lower}{\leftmark}}
  \fancyfoot[\mgr@odd@pos]{\mgr@foot@style\thepage}
  \fancyfoot[\mgr@even@pos]{\mgr@foot@style\thepage} 
 \else 
  \rhead{\raisebox{\mgr@head@lower}{\rightmark}}
	\rfoot{\mgr@foot@style\thepage}
 \fi
}
%--------- do otwarcia rozdzialu ------------%
\fancypagestyle{mgropen}{%
 \fancyhf{}
 \mgr@head@draft@open
 \renewcommand{\headrulewidth}{0pt}
 \if@twoside
  \fancyfoot[\mgr@odd@pos]{\mgr@foot@style\thepage}
  \fancyfoot[\mgr@even@pos]{\mgr@foot@style\thepage}
 \else
  \rfoot{\mgr@foot@style\thepage}\fi}
%--------- do zamkniecia rozdzialu ----------%
\fancypagestyle{mgrclos}{%
 \if@twoside
	\fancyfoot[\mgr@odd@pos]{}
	\fancyfoot[\mgr@even@pos]{}
	\ifmgr@draft\fancyfoot[LO]{}\fancyfoot[RE]{}\lfoot{}\else\fi
 \else
  \rfoot{}\ifmgr@draft\lfoot{}\else\fi\fi}
\newcommand\mgrclosechapter{\par\mbox{}\vspace{-\baselineskip}%
  \ifthenelse{\value{mgr@chapter@page} = \value{page}}{}
  {\thispagestyle{mgrclos}}}  
%--------- do stron z innymi naglowkami ----------%
\newcommand{\mgr@head@other}[2][]{%
 \fancypagestyle{mgrother}{% 
 \fancyhf{}
 \mgr@head@draft
 \renewcommand{\headrulewidth}{\mgr@linia}
 \if@twoside
  \fancyhead[\mgr@odd@pos]{\raisebox{\mgr@head@lower}{\mgr@head@style #2}}
  \fancyhead[\mgr@even@pos]{\raisebox{\mgr@head@lower}{\mgr@head@style #2}}
  \fancyfoot[\mgr@odd@pos]{\mgr@foot@style#1}
  \fancyfoot[\mgr@even@pos]{\mgr@foot@style#1} 
 \else
  \rhead{\raisebox{\mgr@head@lower}{\mgr@head@style #2}}
	\rfoot{\mgr@foot@style#1}\fi
 }}
\newcommand{\mgrthisheadother}[2][]{%
 \mgr@head@other[#1]{#2} 
 \thispagestyle{mgrother}}
\newcommand{\mgrheadother}[2][]{%
 \mgr@head@other[#1]{#2} 
 \thispagestyle{mgrother}
 \pagestyle{mgrother}}
\newcommand{\mgrafterthisheadother}[2][]{%
 \mgr@head@other[#1]{#2} 
 \pagestyle{mgrother}}
\pagestyle{mgrnormal}
%-------------- STONA TYTULOWA ----------------%
%------ Dodatkowe polecenia
\let\@date\@empty
\def\promotor#1{\gdef\@promotor{#1}}
\def\@promotor{\ClassError{mgragh}{Brak promotora pracy!}\@ehc}
\def\nralbumu#1{\gdef\@nralbumu{#1}}
%\def\@nralbumu{\ClassError{mgragh}{Brak numeru albumu!}\@ehc}
\def\slowakluczowe#1{\gdef\@slowakluczowe{#1}}
%\def\@slowakluczowe{\ClassError{mgragh}{Brak slow kluczowych!}\@ehc}
\def\keywords#1{\gdef\@keywords{#1}}
\def\@keywords{\ClassError{mgragh}{Il n'y a pas de mots-cles! (keywords)}\@ehc}
\def\wydzial#1{\gdef\@wydzial{#1}}
\def\@wydzial{\ClassError{mgragh}{Brak wydzialu!}\@ehc}
\def\rok#1{\gdef\@rok{#1}}
\def\@rok{\ClassError{mgragh}{Brak roku!}\@ehc}
\def\specjalnosc#1{\gdef\@specjalnosc{#1}}
\def\@specjalnosc{\ClassError{mgragh}{Brak specjalnosci!}\@ehc}
\def\kierunek#1{\gdef\@kierunek{#1}}
\def\@kierunek{\ClassError{mgragh}{Brak kierunku!}\@ehc}
\def\uczelniaNazwa#1{\gdef\@uczelniaNazwa{#1}}
\def\@uczelniaNazwa{\ClassError{mgragh}{Brak nazwy uczelni!}\@ehc}
\def\uczelniaImienia#1{\gdef\@uczelniaImienia{#1}}
\def\@uczelniaImienia{\ClassError{mgragh}{Brak imienia uczelni!}\@ehc}

\ifmgr@pdftex 
  \def\@orzelek{img/orzelAGH.png}
\else 
  \def\@orzelek{img/orzelAGH.eps}
\renewcommand{\titlesetup}{\huge\bfseries}
\newcommand{\mgr@aut@font}{\LARGE}
\newcommand{\mgr@other@font}{\large}
\newcommand{\mgr@otheri@font}{\normalsize}
\newlength{\mgr@tit@dim}
\settowidth{\mgr@tit@dim}{\mgr@other@font Nr albumu: }
%---- sklad ,,wlasciwy'' ----%
\renewcommand{\maketitle}{\begin{titlepage}%
 \vbox to\textheight{%
  \hyphenpenalty=10000
  \begin{center}%
    \vspace{-3\baselineskip}
     \includegraphics[height=6\baselineskip]{\@orzelek}\par
		\vspace*{\medskipamount}
    \begin{tabular}{c}
      \mgr@other@font \@uczelniaNazwa \\[\smallskipamount]
      \mgr@other@font \@uczelniaImienia \\[\smallskipamount]
      \mgr@other@font w Krakowie
		\end{tabular}\par
  \vspace{\smallskipamount}
  \noindent\rule{\textwidth}{1pt}\\[-2.85\smallskipamount]
  \noindent\rule{\textwidth}{.4pt}\par 
    \vspace*{2\baselineskip}
    {\mgr@aut@font Praca magisterska\par}
     \vspace*{2\baselineskip}
    {\titlesetup\@title\par} %
     \vspace{3\baselineskip} 
    {\LARGE\strut\@author\par}
		\vfill
		\noindent\begin{tabular}[b]{@{}p{\mgr@tit@dim}@{\ }l@{}}
      {\mgr@otheri@font\hfill Kierunek:} & {\mgr@otheri@font \@kierunek} \\
	   {\mgr@otheri@font\hfill Specjalno\m@s\m@c:}& \@specjalnosc
    \end{tabular}\hfill%\mbox{}
	\par\vspace*{\baselineskip}
		\noindent%\begin{tabular}[b]{@{}p{\mgr@tit@dim}@{\ }l@{}}
      %{\mgr@other@font\hfill Nr albumu:}& {\mgr@other@font\@nralbumu} 
    %\end{tabular}
	\hfill
	\hfill
    \begin{tabular}[b]{@{}l@{}} 
      Promotor: \\[\smallskipamount]
      {\mgr@other@font\@promotor}
    \end{tabular}\par 
	 	\vspace*{2.75\baselineskip}	
  \par{\mgr@other@font\@wydzial}\par\vspace{-\smallskipamount}
  \noindent\rule{\textwidth}{.4pt}\\[-2.85\smallskipamount]
  \noindent\rule{\textwidth}{1pt}\\[\smallskipamount]
 {\mgr@other@font Krak{\m@o}w \@rok}
  \end{center} }
%------- Oswiadczenie --------% 
  \newpage\thispagestyle{empty}
    \vspace*{2\baselineskip}
    \begin{center} 
			{\large\bfseries O{\m@s}wiadczenie autora}\par\bigskip
    \end{center}
    {\itshape Ja, ni{\m@z}ej podpisan{\m@k} \@author\ o{\m@s}wiadczam, {\m@z}e praca 
		 ta zosta{\m@l}a napisana samodzielnie i~wykorzystywa{\m@l}a (poza zdobyt{\m@a} na studiach
		 wiedz{\m@a}) jedynie wyniki prac zamieszczonych w~spisie literatury.}\par
	  \vspace{3\baselineskip}
	  \hfill\parbox{15em}{{\small\dotfill}\\[-.3ex] \centerline{\footnotesize(Podpis autora)}}\par
    \vspace{4\baselineskip}
	  \begin{center} 
      {\large\bfseries O{\m@s}wiadczenie promotora}\par\bigskip
	  \end{center}
	    {\itshape O{\m@s}wiadczam, {\m@z}e praca spe{\m@l}nia wymogi stawiane pracom magisterskim.}
	  \par\vspace{3\baselineskip}
	  \hfill\parbox{15em}{{\small\dotfill}\\[-.3ex] \centerline{\footnotesize(Podpis promotora)}}\par
	 \setcounter{page}{0}
   \end{titlepage}%
  \global\let\maketitle\relax
  \global\let\title\relax
  \global\let\author\relax
  \global\let\date\relax
  \global\let\and\relax
  \global\let\promotor\relax
  \global\let\nralbumu\relax
}
\newcounter{mgr@fin@pom}
%----------- Srodowiska do Streszczen -------------%
\newcommand{\mgr@wciecie}[2]{%
 \begin{list}{}{%
  \setlength{\leftmargin}{#1}\setlength{\rightmargin}{0pt}\item\relax}#2%
 \end{list}}
%---------------------------%
\newenvironment{streszczenie}
 {\def\chaptermark##1{\@mkboth{\mgr@head@style ##1}{\mgr@head@style ##1}}
  \chapter*{Streszczenie}
  \setcounter{mgr@fin@pom}{\value{page}}
	\mgrafterthisheadother[\thepage]{Streszczenie}}
 {\par\bigskip%
  \noindent{\bfseries\large S{\m@l}owa kluczowe}\nopagebreak\par\smallskip\nopagebreak% 
  \mgr@wciecie{\parindent}{\@slowakluczowe}\par\mbox{}\vspace{-\baselineskip}%
  \ifthenelse{\value{mgr@fin@pom} = \value{page}}{}{\thispagestyle{mgrclos}}}
%---------------------------%
\newenvironment{abstract}
 {\def\chaptermark##1{\@mkboth{\mgr@head@style ##1}{\mgr@head@style ##1}}
  \chapter*{Abstract}
	\setcounter{mgr@fin@pom}{\value{page}}
	\mgrafterthisheadother[\thepage]{Abstract}}
 {\par\bigskip%
  \noindent{\bfseries\large Key words}\nopagebreak\par\smallskip\nopagebreak% 
  \mgr@wciecie{\parindent}{\@keywords}\par\mbox{}\vspace{-\baselineskip}%
  \ifthenelse{\value{mgr@fin@pom} = \value{page}}{}{\thispagestyle{mgrclos}}}
%-------------- Wstep/Wprowadzenie ----------------%
\newenvironment{wstep}[1][Wst{\m@e}p]
 { \def\chaptermark##1{\@mkboth{\mgr@head@style ##1}{\mgr@head@style ##1}}
   \if@twoside\ifthenelse{\isodd{\value{page}}}{\newpage\mbox{}\thispagestyle{empty}}{}\else\fi
   \chapter*{#1}%
	 \setcounter{mgr@fin@pom}{\value{page}}
	 \mgrafterthisheadother[\thepage]{#1}}
 {\par\mbox{}\vspace{-\baselineskip}
  \ifthenelse{\value{mgr@fin@pom} = \value{page}}{}{\thispagestyle{mgrclos}}%
  \gdef\chaptermark##1{\@mkboth{\mgr@head@style\chaptername\ \thechapter.\  ##1}{}}}
%-------------- Wstep/Wprowadzenie ----------------%
\newenvironment{podsumowanie}[1][Podsumowanie]
 { \def\chaptermark##1{\@mkboth{\mgr@head@style ##1}{\mgr@head@style ##1}}
   \if@twoside\ifthenelse{\isodd{\value{page}}}{\newpage\mbox{}\thispagestyle{empty}}{}\else\fi
   \chapter*{#1}%
	 \setcounter{mgr@fin@pom}{\value{page}}
	 \mgrafterthisheadother[\thepage]{#1}}
 {\par\mbox{}\vspace{-\baselineskip}
  \ifthenelse{\value{mgr@fin@pom} = \value{page}}{}{\thispagestyle{mgrclos}}%
  \gdef\chaptermark##1{\@mkboth{\mgr@head@style\chaptername\ \thechapter.\  ##1}{}}}
%-------------- Spis tresci ----------------%
\newcommand{\mgrtocfont}{\secondarysize}
   %------- Wyglad calosci --------%
\renewcommand\tableofcontents{%
    \def\chaptermark##1{\@mkboth{\mgr@head@style ##1}{\mgr@head@style ##1}}
    \if@twocolumn
      \@restonecoltrue\onecolumn
    \else
      \@restonecolfalse
    \fi
  {\@tempcnta=\c@tocdepth
   \c@tocdepth=-1
\chapter*{\contentsname}%
    \setcounter{mgr@fin@pom}{\value{page}}
		\mgrafterthisheadother[\thepage]{\contentsname}
    \c@tocdepth=\@tempcnta
    \mgrtocfont
    \gdef\previous@toc@level{-1000}%
    \@starttoc{toc}
		\ifthenelse{\value{mgr@fin@pom} = \value{page}}{}{\thispagestyle{mgrclos}}}%
    \if@restonecol\twocolumn\fi}
   %--- Zmiana wygladu spisu tresci ---%
\def\mw@tocline#1#2#3#4#5{%
  \ifnum #1>\c@tocdepth \else
    \mw@tocskip{#1}{\z@ \@plus.2\p@}%
    {\leftskip #2\relax
     \@tempdima #3\relax
     \advance\leftskip \@tempdima
     \rightskip \@tocrmarg \parfillskip -\rightskip
     \interlinepenalty\@M
     \noindent\kern-\@tempdima{\ignorespaces#4}\nobreak
     \leaders\hbox{$\m@th
        \mkern \@dotsep mu\hbox{.}\mkern \@dotsep
        mu$}\hfill
     \nobreak
     \hb@xt@\@pnumwidth{\hfil\normalfont \normalcolor #5}%
     \par}%
  \fi}
\def\numberline#1{\hb@xt@\@tempdima{#1\hfil}\ignorespaces}
    %------- szerokosci numerow --------%
\newlength{\mgr@sectocwd}       \settowidth{\mgr@sectocwd}{9.9\enspace}
\newlength{\mgr@subsectocwd}    \settowidth{\mgr@subsectocwd}{9.9.9\enspace}
\newlength{\mgr@subsubsectocwd} \settowidth{\mgr@subsubsectocwd}{9.9.9.10\enspace}
    %------- sklad poszczegolnych czesci --------%
\renewcommand*\l@part[2]{%
  \ifnum\c@tocdepth >-2\relax
    \mw@tocskip{-1}{.6\baselineskip}%
    \begingroup
      \parindent\z@ \rightskip \@pnumwidth
      \parfillskip -\@pnumwidth
      {\leavevmode\normalsize
        \def\numberline##1{##1\enspace}%
        \kern1.5em\normalsize\bfseries\partname\ #1\hfil\null%
        }\par
       \nobreak
         \global\@nobreaktrue
         \everypar{\global\@nobreakfalse\everypar{}}%
    \endgroup
  \addvspace{.4\baselineskip}%
  \fi}
\renewcommand*\l@chapter[2]{\addvspace{.3\baselineskip}%
        \mw@tocline{0}{0pt}{1.5em}{{\sectsetup\normalsize #1}}{#2}%
        \addvspace{.2\baselineskip}}
\renewcommand*\l@section{\mw@tocline{1}{1.25em}{\mgr@sectocwd}}
\renewcommand*\l@subsection{\mw@tocline{2}{2.25em}{\mgr@subsectocwd}}
\renewcommand*\l@subsubsection{\mw@tocline{3}{3.25em}{\mgr@subsubsectocwd}}
\renewcommand*\l@paragraph{\mw@tocline{4}{10em}{5em}}
\renewcommand*\l@subparagraph{\mw@tocline{5}{12em}{6em}}
%-------------- Przedefiniowanie indeksu ----------------%
\renewenvironment{theindex}{%
	\def\chaptermark##1{\@mkboth{\mgr@head@style ##1}{\mgr@head@style ##1}}
    \if@twocolumn
        \@restonecolfalse
      \else
        \@restonecoltrue
      \fi
    \columnseprule \z@
    \columnsep 35\p@
    \twocolumn
    \chapter*{\indexname}%
    \mgrafterthisheadother[\thepage]{\indexname}
		\parindent\z@
    \parskip\z@ \@plus .3\p@\relax
    \let\item\@idxitem
    \secondarysize}%
 {\ifthenelse{\value{mgr@fin@pom} = \value{page}}{}{\thispagestyle{mgrclos}}
  \if@restonecol\onecolumn\else\clearpage\fi}
%------------ powrot do kropki w wyliczeniu ----------------%
\renewcommand{\labelitemi}{\textbullet}
%-------------- dluzsza linia po przypisach ----------------%
\renewcommand\footnoterule{%
  \kern-3\p@
  \hrule\@width.4\textwidth
  \kern2.6\p@}
%------------ kropka po numerze twierdzenia ----------------%
\def\@begintheorem#1#2{\trivlist
   \item[\hskip \labelsep{\bfseries #1\ #2.}]\itshape} 
\def\@opargbegintheorem#1#2#3{\trivlist
      \item[\hskip \labelsep{\bfseries #1\ #2\ (#3).}]\itshape} 
%-----------------------------------------------------------%
\endinput
%% End of file 'mgragh.cls'
