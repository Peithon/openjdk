/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/* @test
   @bug 4913702
   @summary validates canEncode(char c) method for sun.nio.cs.Shift_JIS
 */


import java.nio.*;
import java.nio.charset.*;

public class SJISCanEncode {
    private Charset cs;
    private CharsetEncoder encoder;

    private void canEncodeTest(char inputChar,
                               boolean expectedResult)
                               throws Exception {
        String msg = "err: Shift_JIS canEncode() return value ";

        if (encoder.canEncode(inputChar) != expectedResult) {
            throw new Exception(msg + !(expectedResult) +
                ": "  + Integer.toHexString((int)inputChar));
        }
    }

    public static void main(String[] args) throws Exception {
        SJISCanEncode test = new SJISCanEncode();
        test.cs = Charset.forName("SJIS");
        test.encoder = test.cs.newEncoder();

        // us-ascii (mappable by Shift_JIS)
        test.canEncodeTest('\u0001', true);

        // Halfwidth Katakana
        test.canEncodeTest('\uFF01', true);

        // CJK ideograph
        test.canEncodeTest('\u4E9C', true);

        //Hiragana
        test.canEncodeTest('\u3041', true);
        // fullwidth Katakana
        test.canEncodeTest('\u30A1', true);

        // U+0080 should be unmappable
        // U+4000 is a BMP character not covered by Shift_JISe

        test.canEncodeTest('\u0080', false);
        test.canEncodeTest('\u4000', false);
    }
}
