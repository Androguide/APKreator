/**   Copyright (C) 2013  Louis Teboul (a.k.a Androguide)
 *
 *    admin@pimpmyrom.org  || louisteboul@gmail.com
 *    http://pimpmyrom.org || http://androguide.fr
 *    71 quai Clémenceau, 69300 Caluire-et-Cuire, FRANCE.
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License along
 *      with this program; if not, write to the Free Software Foundation, Inc.,
 *      51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 **/

package com.androguide.apkreator.pluggable.parsers;

import java.util.ArrayList;

public interface ParserInterface {

    public ArrayList<String> title = new ArrayList<String>(),
            desc = new ArrayList<String>(),
            type = new ArrayList<String>(),
            control = new ArrayList<String>(),
            unit = new ArrayList<String>(),
            prop = new ArrayList<String>(),
            shellCmds = new ArrayList<String>(),
            shellCmds2 = new ArrayList<String>(),
            on = new ArrayList<String>(),
            off = new ArrayList<String>(),
            buttons = new ArrayList<String>(),
            buttons2 = new ArrayList<String>(),
            urls = new ArrayList<String>(),
            paths = new ArrayList<String>(),
            stripeColor = new ArrayList<String>();

    public ArrayList<Integer> min = new ArrayList<Integer>(),
            max = new ArrayList<Integer>(),
            def = new ArrayList<Integer>();

    public ArrayList<String[]> props = new ArrayList<String[]>();

    public ArrayList<ArrayList<String>> spinners = new ArrayList<ArrayList<String>>(), spinnerCmds = new ArrayList<ArrayList<String>>(),  spinnerEntries = new ArrayList<ArrayList<String>>();
}